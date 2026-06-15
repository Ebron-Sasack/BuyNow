package com.buynow.service.impl;

import com.buynow.entity.User;
import com.buynow.repository.UserRepository;
import com.buynow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public ResponseEntity<User> loginUser(String userEmail, String userpassword) {
        return null;
    }

    @Override
    public ResponseEntity<User> getUserById(Long id) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return List.of();
    }

    @Override
    public ResponseEntity<User> updateUser(Long id, User user) {
        return null;
    }

    @Override
    public void deleteUser() {

    }
}
