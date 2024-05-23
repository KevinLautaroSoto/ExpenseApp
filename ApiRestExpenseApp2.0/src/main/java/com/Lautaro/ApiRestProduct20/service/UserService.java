package com.Lautaro.ApiRestProduct20.service;

import com.Lautaro.ApiRestProduct20.models.User;
import com.Lautaro.ApiRestProduct20.repository.UserRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> createUser(User user) {
        return userRepository.createUser(user);
    }

    public List<User> getAllUser() {
        return userRepository.getAllUser();
    }

    public User getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    public ResponseEntity<String> updateUser(Long id, User user) {
        return userRepository.updateUser(id, user);
    }

    public ResponseEntity<String> deleteUser(Long id) {
        return userRepository.deleteUser(id);
    }
}
