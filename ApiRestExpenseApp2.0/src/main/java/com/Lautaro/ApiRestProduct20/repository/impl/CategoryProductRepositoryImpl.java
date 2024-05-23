package com.Lautaro.ApiRestProduct20.repository.impl;

import com.Lautaro.ApiRestProduct20.models.CategoryProduct;
import com.Lautaro.ApiRestProduct20.repository.CategoryProductRepository;
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
public class CategoryProductRepositoryImpl implements CategoryProductRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String INSERT_INTO_CATEGORY_PRODUCTS = "INSERT INTO Category_Product (name) VALUES (?)";
    private static final String SELECT_ALL_CATEGORY_PRODUCTS = "SELECT * FROM Category_Product";
    private static final String SELECT_CATEGORY_PRODUCT_BY_ID = "SELECT * FROM Category_Product WHERE id = ?";
    private static final String UPDATE_CATEGORY_PRODUCT = "UPDATE Category_Product SET name = ? WHERE id = ?";
    private static final String DELETE_CATEGORY_PRODUCT = "DELETE FROM Category_Product WHERE id = ?";

    @Autowired
    public CategoryProductRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //*1
    private static final class CategoryProductRowMapper implements RowMapper<CategoryProduct> {
        @Override
        public CategoryProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
            CategoryProduct categoryProduct = new CategoryProduct();
            categoryProduct.setId(rs.getLong("id"));
            categoryProduct.setName(rs.getString("name"));
            return categoryProduct;
        }
    }

    @Override
    public ResponseEntity<String> createCategoryProduct(CategoryProduct categoryProduct) {
        try {
            jdbcTemplate.update(INSERT_INTO_CATEGORY_PRODUCTS, categoryProduct.getName());
            return ResponseEntity.ok("CategoryProdyct created successfully.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(500).body("Error creating CategoryProduct into the database.");
        }
    }

    @Override
    public List<CategoryProduct> getAllCategoryProduct() {
        return jdbcTemplate.query(SELECT_ALL_CATEGORY_PRODUCTS, new CategoryProductRowMapper());
    }

    @Override
    public CategoryProduct getCategoryProductById(Long id) {
        List<CategoryProduct> results = jdbcTemplate.query(
                SELECT_CATEGORY_PRODUCT_BY_ID,
                ps -> ps.setLong(1, id),
                new CategoryProductRowMapper()
        );
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public ResponseEntity<String> updateCategoryProduct(Long id, CategoryProduct newCategoryProduct) {
        try {
            int rowsAffected = jdbcTemplate.update(UPDATE_CATEGORY_PRODUCT, newCategoryProduct.getName(),id);
            if (rowsAffected > 0) {
                return ResponseEntity.ok("CategoryProduct successfully updated.");
            } else {
                return ResponseEntity.status(404).body("CategoryProduct not found");
            }
        } catch (DataAccessException e) {
            return ResponseEntity.status(500).body("Error updating CategoryProduct: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> deleteCategoryProduct(Long id) {
        try {
            int rowsAffected = jdbcTemplate.update(DELETE_CATEGORY_PRODUCT, id);
            if (rowsAffected > 0) {
                return ResponseEntity.ok("CategoryProduct deleted succesffully.");
            } else {
                return ResponseEntity.status(404).body("CategoryProduct not found.");
            }
        } catch(DataAccessException e) {
            return ResponseEntity.status(500).body("Error deleting CategoryProduct: " + e.getMessage());
        }
    }
}

/*
NOTAS
*1 La clase CategoryExpenseRowMapper es una implementación de RowMapper que convierte cada fila de un ResultSet
en una instancia de CategoryExpense. El método mapRow es llamado por el JdbcTemplate para cada fila del ResultSet
y se encarga de extraer los valores de las columnas id y name, asignándolos a un nuevo objeto CategoryExpense, que
luego es retornado.
 */
