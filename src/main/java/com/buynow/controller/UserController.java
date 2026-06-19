package com.buynow.controller;

import com.buynow.dto.UserDto;
import com.buynow.entity.User;
import com.buynow.exception.AlreadyExistsException;
import com.buynow.exception.ResourceNotFoundException;
import com.buynow.repository.UserRepository;
import com.buynow.request.UserRequest;
import com.buynow.request.UserUpdateRequest;
import com.buynow.response.ApiResponse;
import com.buynow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/id")
    public ResponseEntity<ApiResponse> getUserById(@RequestParam Long userId) {
        try {
            User user = userService.getUserById(userId);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("Sucess", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody UserRequest userRequest) {
        try {
            User user = userService.createUser(userRequest);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("User Created Sucess", userDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestParam Long userId, @RequestBody UserUpdateRequest userUpdateRequest) {
        try {
            User user = userService.updateUser(userId,userUpdateRequest);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("User Updated Sucess", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteUser(@RequestParam Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("User Deleted Sucess", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
