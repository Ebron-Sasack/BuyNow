package com.buynow.controller;

import com.buynow.dto.UserDto;
import com.buynow.entity.User;
import com.buynow.request.UserLoginRequest;
import com.buynow.request.UserRequest;
import com.buynow.request.UserUpdateRequest;
import com.buynow.payload.ApiResponse;
import com.buynow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/id")
    public ResponseEntity<ApiResponse> getUserById(@RequestParam Long userId) {
            User user = userService.getUserById(userId);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("Sucess", userDto));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> createUser(@RequestBody UserRequest userRequest) {
            User user = userService.createUser(userRequest);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("User Created Sucess", userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loginUser(@RequestBody UserLoginRequest  request) {
        String userDto = userService.loginUser(request);
        return ResponseEntity.ok(new ApiResponse("User Login Sucess", userDto));
    }


    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestParam Long userId, @RequestBody UserUpdateRequest userUpdateRequest) {
            User user = userService.updateUser(userId,userUpdateRequest);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("User Updated Sucess", userDto));
    }


    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteUser(@RequestParam Long userId) {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("User Deleted Sucess", null));
    }
}
