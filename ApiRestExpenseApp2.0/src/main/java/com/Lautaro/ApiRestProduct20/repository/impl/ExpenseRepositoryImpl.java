package com.Lautaro.ApiRestProduct20.repository.impl;

import com.Lautaro.ApiRestProduct20.models.Expense;
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

    private static final String INSERT_INTO_EXPENSE = "INSERT INTO Expense (description, totalAmount, date) VALUES (?, ?, ?)";
    private static final String SELECT_ALL_EXPENSE = "SELECT * FROM Expense";
    private static final String SELECT_EXPENSE_BY_ID = "SELECT * FROM Expense WHERE id = ?";
    private static final String UPDATE_EXPENSE = "UPDATE Expense SET description = ?, totalAmount = ?, date = ? WHERE id = ?";
    private static final String DELETE_UPDATE = "DELETE FROM Expense WHERE id = ?";

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
            jdbcTemplate.update(INSERT_INTO_EXPENSE,
                    expense.getDescription(),
                    expense.getTotalAmount(),
                    expense.getDate());
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
    public ResponseEntity<String> udpateExpense(Long id, Expense newExpense) {
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
