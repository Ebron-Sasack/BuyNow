package com.buynow.service;

import com.buynow.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    public User registerUser(User user);
    public ResponseEntity<User> loginUser(String userEmail, String userpassword);
    public ResponseEntity<User> getUserById(Long id);
    public List<User> getAllUsers();
    public ResponseEntity<User> updateUser(Long id, User user);
    public void deleteUser();
//    public void changePassword();

}
