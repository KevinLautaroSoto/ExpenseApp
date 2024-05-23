package com.Lautaro.ApiRestProduct20.repository.impl;

import com.Lautaro.ApiRestProduct20.models.User;
import com.Lautaro.ApiRestProduct20.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    private static final String INSERT_INTO_USER = "INSERT INTO Users (name, lastname, email) VALUES (?, ?, ?)";
    private static final String SELECT_ALL_USERS = "SELECT * FROM Users";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM Users WHERE id = ?";
    private static final String UPDATE_USER = "UPDATE Users SET name = ?, lastname = ?, email = ? VALUES (?, ?, ?)";
    private static final String DELETE_USER = "DELETE FROM Users WHERE id = ?";

    @Autowired
    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setLastname(rs.getString("lastname"));
            user.setEmail(rs.getString("email"));
            return user;
        }
    }

    @Override
    public ResponseEntity<String> createUser(User user) {
        try {
            jdbcTemplate.update(INSERT_INTO_USER,
                    user.getName(),
                    user.getLastname(),
                    user.getEmail());
            return ResponseEntity.ok("User created successfully.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(500).body("Error creating User.");
        }
    }

    @Override
    public List<User> getAllUser() {
        return jdbcTemplate.query(SELECT_ALL_USERS, new UserRowMapper());
    }

    @Override
    public User getUserById(Long id) {
        List<User> results = jdbcTemplate.query(
                SELECT_USER_BY_ID,
                ps -> ps.setLong(1, id),
                new UserRowMapper()
        );
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public ResponseEntity<String> updateUser(Long id, User newUser) {
        try {
            int rowsAffected = jdbcTemplate.update(
                    UPDATE_USER,
                    newUser.getName(),
                    newUser.getLastname(),
                    newUser.getEmail(),
                    id
            );
            if (rowsAffected > 0) {
                return ResponseEntity.ok("User updated successfully.");
            } else {
                return ResponseEntity.status(404).body("User not found.");
            }
        } catch (DataAccessException e) {
            return ResponseEntity.status(500).body("Error updating User: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> deleteUser(Long id) {
        try {
            int rowsAffected = jdbcTemplate.update(
                    DELETE_USER,
                    id
            );
            if (rowsAffected > 0) {
                return ResponseEntity.ok("User deleted successfully.");
            } else {
                return ResponseEntity.status(404).body("User not found.");
            }
        } catch (DataAccessException e) {
            return ResponseEntity.status(500).body("Error deleting user: " + e.getMessage());
        }
    }
}
