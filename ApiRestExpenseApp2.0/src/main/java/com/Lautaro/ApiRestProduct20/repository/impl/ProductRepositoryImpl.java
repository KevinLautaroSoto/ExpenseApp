package com.Lautaro.ApiRestProduct20.repository.impl;

import com.Lautaro.ApiRestProduct20.models.*;
import com.Lautaro.ApiRestProduct20.repository.ProductRepository;
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
public class ProductRepositoryImpl implements ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    private static final String INSERT_INTO_PRODUCTS = "INSERT INTO Products (name, price, category_product_id, expense_id) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_PRODUCTS =
            "SELECT p.id, p.name, p.price, " +
                    "cp.id AS cp_id, cp.name AS cp_name, " +
                    "e.id AS e_id, e.description AS e_description, e.total_amount AS e_total_amount, e.date AS e_date, " +
                    "u.id AS u_id, u.username AS u_username, " +
                    "ce.id AS ce_id, ce.name AS ce_name " +
                    "FROM Products p " +
                    "LEFT JOIN Category_Product cp ON p.category_product_id = cp.id " +
                    "LEFT JOIN Expenses e ON p.expense_id = e.id " +
                    "LEFT JOIN Users u ON e.user_id = u.id " +
                    "LEFT JOIN Category_Expense ce ON e.category_expense_id = ce.id";
    private static final String SELECT_PRODUCTS_BY_ID =
            "SELECT p.id, p.name, p.price, " +
                    "cp.id AS cp_id, cp.name AS cp_name, " +
                    "e.id AS e_id, e.description AS e_description, e.total_amount AS e_total_amount, e.date AS e_date, " +
                    "u.id AS u_id, u.username AS u_username, " +
                    "ce.id AS ce_id, ce.name AS ce_name " +
                    "FROM Products p " +
                    "LEFT JOIN Category_Product cp ON p.category_product_id = cp.id " +
                    "LEFT JOIN Expense e ON p.expense_id = e.id " +
                    "LEFT JOIN User u ON e.user_id = u.id " +
                    "LEFT JOIN Category_Expense ce ON e.category_expense_id = ce.id " +
                    "WHERE p.id = ?";
    private static final String UPDATE_PRODUCT = "UPDATE Products SET name = ?, price = ?, category_product_id = ?, expense_id = ? WHERE id = ?";
    private static final String DELETE_PRODUCT = "DELETE FROM Products WHERE id = ?";

    @Autowired
    public ProductRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ResponseEntity<String> createProduct(Product product) {
        try {
            Long categoryId = product.getCategoryProduct() != null ? product.getCategoryProduct().getId() : null;
            Long expenseId = product.getExpense() != null ? product.getExpense().getId() : null;
            jdbcTemplate.update(INSERT_INTO_PRODUCTS, product.getName(), product.getPrice(), categoryId, expenseId);
            return ResponseEntity.ok("Product successfully created.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(500).body("Error creating a Product: " + e.getMessage());
        }
    }

    @Override
    public List<Product> getAllProduct() {
        return jdbcTemplate.query(SELECT_ALL_PRODUCTS, new ProductRowMapper());
    }

    @Override
    public Product getProductById(Long id) {
        List<Product> results = jdbcTemplate.query(
                SELECT_PRODUCTS_BY_ID,
                ps -> ps.setLong(1, id),
                new ProductRowMapper()
        );
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public ResponseEntity<String> updateProduct(Long id, Product product) {
        try {
            int rowsAffected = jdbcTemplate.update(UPDATE_PRODUCT,
                    product.getName(),
                    product.getPrice(),
                    product.getCategoryProduct() != null ? product.getCategoryProduct().getId() : null,
                    product.getExpense() != null ? product.getExpense().getId() : null,
                    id);

            if (rowsAffected > 0) {
                return ResponseEntity.ok("Product successfully updated.");
            } else {
                return ResponseEntity.status(404).body("Product not found.");
            }
        } catch (DataAccessException e) {
            return ResponseEntity.status(500).body("Error updating product: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> deleteProduct(Long id) {
        try {
            int rowsAffected = jdbcTemplate.update(DELETE_PRODUCT, id);
            if (rowsAffected > 0) {
                return ResponseEntity.ok("Product deleted successfully.");
            } else {
                return ResponseEntity.status(404).body("Product not found");
            }
        } catch (DataAccessException e) {
            return ResponseEntity.status(500).body("Error deleting product: " + e.getMessage());
        }
    }

    private static final class ProductRowMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setId(rs.getLong("id"));
            product.setName(rs.getString("name"));
            product.setPrice(rs.getDouble("price"));

            Long categoryProductId = rs.getLong("cp_id");
            if (categoryProductId != null && categoryProductId > 0) {
                CategoryProduct categoryProduct = new CategoryProduct();
                categoryProduct.setId(categoryProductId);
                categoryProduct.setName(rs.getString("cp_name"));
                product.setCategoryProduct(categoryProduct);
            } else {
                product.setCategoryProduct(null);
            }

            Long expenseId = rs.getLong("e_id");
            if (expenseId != null && expenseId > 0) {
                Expense expense = new Expense();
                expense.setId(expenseId);
                expense.setDescription(rs.getString("e_description"));
                expense.setTotalAmount(rs.getDouble("e_total_amount"));
                expense.setDate(rs.getDate("e_date"));

                Long userId = rs.getLong("u_id");
                if (userId != null && userId > 0) {
                    User user = new User();
                    user.setId(userId);
                    user.setUsername(rs.getString("u_username"));
                    expense.setUser(user);
                } else {
                    expense.setUser(null);
                }

                Long categoryExpenseId = rs.getLong("ce_id");
                if (categoryExpenseId != null && categoryExpenseId > 0) {
                    CategoryExpense categoryExpense = new CategoryExpense();
                    categoryExpense.setId(categoryExpenseId);
                    categoryExpense.setName(rs.getString("ce_name"));
                    expense.setCategoryExpense(categoryExpense);
                } else {
                    expense.setCategoryExpense(null);
                }

                product.setExpense(expense);
            } else {
                product.setExpense(null);
            }

            return product;
        }
    }
}
