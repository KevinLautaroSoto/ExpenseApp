package com.Lautaro.ApiRestProduct20.repository.impl;

import com.Lautaro.ApiRestProduct20.models.Expense;
import com.Lautaro.ApiRestProduct20.models.Product;
import com.Lautaro.ApiRestProduct20.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ExpenseRepositoryImpl implements ExpenseRepository {
    private final JdbcTemplate jdbcTemplate;

    private static final String INSERT_INTO_EXPENSE = "INSERT INTO Expenses (description, total_amount, date, user_id, category_expense_id) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_EXPENSE = "\"SELECT e.id AS expense_id, e.description AS expense_description, \" +\n" +
            "        \"e.totalAmount AS expense_totalAmount, e.date AS expense_date, \" +\n" +
            "        \"u.id AS user_id, u.username AS user_username, u.email AS user_email, \" +\n" +
            "        \"ce.id AS categoryExpense_id, ce.name AS categoryExpense_name, \" +\n" +
            "        \"p.id AS product_id, p.name AS product_name, p.price AS product_price, \" +\n" +
            "        \"cp.id AS categoryProduct_id, cp.name AS categoryProduct_name \" +\n" +
            "        \"FROM Expenses e \" +\n" +
            "        \"LEFT JOIN Users u ON e.user_id = u.id \" +\n" +
            "        \"LEFT JOIN CategoryExpense ce ON e.category_expense_id = ce.id \" +\n" +
            "        \"LEFT JOIN Products p ON e.id = p.expense_id \" +\n" +
            "        \"LEFT JOIN CategoryProduct cp ON p.category_product_id = cp.id \" +\n" +
            "        \"ORDER BY e.id, p.id\";";
    private static final String SELECT_EXPENSE_BY_ID = "SELECT * FROM Expense WHERE id = ?";
    private static final String UPDATE_EXPENSE = "UPDATE Expense SET description = ?, totalAmount = ?, date = ? WHERE id = ?";
    private static final String DELETE_UPDATE = "DELETE FROM Expense WHERE id = ?";
    private static final String INSERT_INTO_PRODUCTS = "INSERT INTO Products (name, price, category_product_id, expense_id) " +
            "VALUES (?, ?, ?, ?)";

    @Autowired
    public ExpenseRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final class ExpenseRowMapper implements RowMapper<Expense> {
        @Override
        public Expense mapRow(ResultSet rs, int rowNum) throws SQLException {
            Expense expense = new Expense();
            expense.setId(rs.getLong("id"));
            expense.setDescription(rs.getString("description"));
            expense.setTotalAmount(rs.getDouble("totalAmount"));
            expense.setDate(rs.getDate("date"));
            return expense;
        }
    }

    @Override
    public ResponseEntity<String> createExpense(Expense expense) {
        try {
            //Validar que el user y categoryExpense no sean nulos.
            if (expense.getUser() == null || expense.getCategoryExpense() == null) {
                return  ResponseEntity.status(400).body("User and Category Expense must not be null.");
            }

            //Validar que el user y categoryExpense tengan IDs.
            if (expense.getUser().getId() == null || expense.getCategoryExpense().getId() == null) {
                return ResponseEntity.status(400).body("User ID and CategoryExpense ID must not be null.");
            }

            jdbcTemplate.update(INSERT_INTO_EXPENSE,
                    expense.getDescription(),
                    expense.getTotalAmount(),
                    expense.getDate(),
                    expense.getUser().getId(),
                    expense.getCategoryExpense().getId());

            //Obtiene el Id de la Expense recién creada.
            Long expenseId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

            //Inserto los productos asosciado a la expense.
            for (Product product : expense.getProducts()) {
                jdbcTemplate.update(INSERT_INTO_PRODUCTS,
                        product.getName(),
                        product.getPrice(),
                        product.getCategoryProduct().getId(),
                        expenseId);
            }
            return ResponseEntity.ok("Expense successfully created.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(500).body("Error creating expense: " + e.getMessage());
        }
    }

    @Override
    public List<Expense> getAllExpense() {
        return jdbcTemplate.query(SELECT_ALL_EXPENSE, new ExpenseRowMapper());
    }

    @Override
    public Expense getExpenseById(Long id) {
        List<Expense> results = jdbcTemplate.query(
                SELECT_EXPENSE_BY_ID,
                ps -> ps.setLong(1, id),
                new ExpenseRowMapper()
        );
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public ResponseEntity<String> updateExpense(Long id, Expense newExpense) {
        try {
            int rowsAffected = jdbcTemplate.update(
                    UPDATE_EXPENSE,
                    newExpense.getDescription(),
                    newExpense.getTotalAmount(),
                    newExpense.getDate(),
                    id);
            if (rowsAffected > 0) {
                return ResponseEntity.ok("Expense updated successfully.");
            } else {
                return ResponseEntity.status(404).body("Expense not found.");
            }
        } catch (DataAccessException e) {
            return ResponseEntity.status(500).body("Error updating Expense.");
        }
    }

    @Override
    public ResponseEntity<String> deleteExpense(Long id) {
        try {
            int rowsAffected = jdbcTemplate.update(DELETE_UPDATE, id);
            if (rowsAffected > 0) {
                return ResponseEntity.ok("Expense deleted successfully.");
            } else {
                return ResponseEntity.status(404).body("Expense not found.");
            }
        } catch (DataAccessException e) {
            return ResponseEntity.status(500).body("Error deleting Expense.");
        }
    }
}
