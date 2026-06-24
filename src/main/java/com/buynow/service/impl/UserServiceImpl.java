package com.buynow.service.impl;

import com.buynow.dto.UserDto;
import com.buynow.entity.Role;
import com.buynow.enums.RoleType;
import com.buynow.repository.RoleRepository;
import com.buynow.request.UserLoginRequest;
import com.buynow.request.UserRequest;
import com.buynow.request.UserUpdateRequest;
import com.buynow.entity.User;
import com.buynow.exception.AlreadyExistsException;
import com.buynow.exception.ResourceNotFoundException;
import com.buynow.repository.UserRepository;
import com.buynow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("User Not Found")
        );
    }

    @Override
    public User createUser(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new AlreadyExistsException(
                    "Oops! " + userRequest.getEmail() + " User Already Exists"
            );
        }

        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());

        Role role = roleRepository.findByRoleName(RoleType.BUYER)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role not found"));

        user.setRole(role);
        return userRepository.save(user);
    }

    @Override
    public UserDto loginUser(UserLoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invalid Email or Password"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid Email or Password");
        }

        return convertUserToDto(user);
    }

    @Override
    public User updateUser(Long id, UserUpdateRequest userUpdateRequest) {
        return userRepository.findById(id).map(
                existingUser -> {
                    existingUser.setFirstName(userUpdateRequest.getFirstName());
                    existingUser.setLastName(userUpdateRequest.getLastName());
                    return userRepository.save(existingUser);
                }).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id).ifPresentOrElse(userRepository::delete,
                ()-> {
                    throw new ResourceNotFoundException("User Not Found");
                });
    }

    @Override
    public UserDto convertUserToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
