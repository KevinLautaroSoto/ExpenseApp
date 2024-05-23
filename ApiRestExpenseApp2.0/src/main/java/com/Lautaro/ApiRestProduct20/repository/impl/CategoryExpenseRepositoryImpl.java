package com.Lautaro.ApiRestProduct20.repository.impl;

import com.Lautaro.ApiRestProduct20.models.CategoryExpense;
import com.Lautaro.ApiRestProduct20.repository.CategoryExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CategoryExpenseRepositoryImpl implements CategoryExpenseRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String INSERT_INTO_EXPENSE_CATEGORIES = "INSERT INTO Category_Expense (name) VALUES (?)";
    private static final String SELECT_ALL_EXPENSE_CATEGORIES = "SELECT * FROM Category_Expense";
    private static final String SELECT_EXPENSE_CATEGORY_BY_ID = "SELECT * FROM Category_Expense WHERE id = ?";
    private static final String UPDATE_EXPENSE_CATEGORY = "UPDATE Category_Expense SET name = ? WHERE id = ?";
    private static final String DELETE_EXPENSE_CATEGORY = "DELETE FROM Category_Expense WHERE id = ?";

    @Autowired
    public CategoryExpenseRepositoryImpl(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    private static final class CategoryExpenseRowMapper implements RowMapper<CategoryExpense> {
        @Override
        public CategoryExpense mapRow(ResultSet rs, int rowNum) throws SQLException {
            CategoryExpense categoryExpense = new CategoryExpense();
            categoryExpense.setId(rs.getLong("id"));
            categoryExpense.setName(rs.getString("name"));
            return categoryExpense;
        }
    }

    @Override
    public ResponseEntity<String> createCategoryExpense(CategoryExpense categoryExpense) {
        try {
            jdbcTemplate.update(INSERT_INTO_EXPENSE_CATEGORIES, categoryExpense.getName());
            return ResponseEntity.ok("CategoryExpense successfully created into the database.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(500).body("Error creating CategoryExpense: " + e.getMessage());
        }
    }

    @Override
    public List<CategoryExpense> getAllCategoryExpense() {
        return jdbcTemplate.query(SELECT_ALL_EXPENSE_CATEGORIES, new CategoryExpenseRowMapper());
    }

    @Override
    public CategoryExpense getCategoryExpenseById(Long id) {

        //Utiliza JdbcTemplate.query con un PreparedStatementSetter y Rowmapper
        List<CategoryExpense> results = jdbcTemplate.query(
                SELECT_EXPENSE_CATEGORY_BY_ID,
                ps -> ps.setLong(1, id),//una expresin lambda que establece el valor del parametro
                new CategoryExpenseRowMapper()//mapea el resultado de la consulta a un objeto CategoryExpense4
        );
        //Si obtengo resultado retorono el primero resultado, de lo contrario retorno null.
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public ResponseEntity<String> updateCategoryExpense(Long id, CategoryExpense newCategoryExpense) {
        try {
            //Ejecuto la consulta sql almacenada en la variable UPDATE_EXPENSE_CATEGORY pasandole el nuevo nombre y id.
            int rowsAffected = jdbcTemplate.update(UPDATE_EXPENSE_CATEGORY, newCategoryExpense.getName(), id);
            //Retorna una respuesta dependiendo si se actualizo o no alguna fila.
            if (rowsAffected > 0 ) {
                return ResponseEntity.ok("CategoryExpense updated successfully.");
            } else {
                //si no se encontro ninguna categoria de gasto con ese id avisa que no se ha encontrado.
                return ResponseEntity.status(404).body("CategoryExpense not found.");
            }
        } catch (DataAccessException e) {
            //retorna una respuesta de error si ocurre una excepcion.
            return ResponseEntity.status(500).body("Error updating CategoryExpense: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> deleteCategoryExpense(Long id) {
        try {
            //ejecuto la consulta pasandole el id como parametro.
            int rowsAffected = jdbcTemplate.update(DELETE_EXPENSE_CATEGORY, id);
            //Retorno respuesta dependiendo si se afecto a alguna fila o no.
            if (rowsAffected > 0) {
                return ResponseEntity.ok("ExpenseCategory successfully deleted from the database.");
            } else {
                return ResponseEntity.status(404).body("ExpenseCategory not found with that id.");
            }
        } catch (DataAccessException e) {
            return ResponseEntity.status(500).body("Error deleting ExpenseCategory: " + e.getMessage());
        }
    }
}

/*
@Override
    public ResponseEntity<String> createCategoryExpense(CategoryExpense categoryExpense) {
        int result = jdbcTemplate.update(INSERT_INTO_EXPENSE_CATEGORIES, categoryExpense.getName());
        if (result > 0) {
            return new ResponseEntity<>("CategoryExpense created successfully.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to create CategoryExpense.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
 */
