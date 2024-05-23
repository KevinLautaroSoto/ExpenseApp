package com.Lautaro.ApiRestProduct20.repository;

import com.Lautaro.ApiRestProduct20.models.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserRepository {
    ResponseEntity<String> createUser(User user);
    List<User> getAllUser();
    User getUserById(Long id);
    ResponseEntity<String> updateUser(Long id, User newUser);
    ResponseEntity<String> deleteUser(Long id);
}
