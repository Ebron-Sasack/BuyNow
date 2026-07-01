package com.buynow.service;

import com.buynow.dto.UserDto;
import com.buynow.request.UserLoginRequest;
import com.buynow.request.UserRequest;
import com.buynow.request.UserUpdateRequest;
import com.buynow.entity.User;

public interface UserService {

    User getUserById(Long id);
    User createUser(UserRequest userRequest);

    String loginUser(UserLoginRequest request);

    User updateUser(Long id, UserUpdateRequest userUpdateRequest);
    void deleteUser(Long id);

    UserDto convertUserToDto(User user);
}
