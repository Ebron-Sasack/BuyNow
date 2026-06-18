package com.buynow.service.impl;

import com.buynow.request.UserRequest;
import com.buynow.request.UserUpdateRequest;
import com.buynow.entity.User;
import com.buynow.exception.AlreadyExistsException;
import com.buynow.exception.ResourceNotFoundException;
import com.buynow.repository.UserRepository;
import com.buynow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("User Not Found")
        );
    }

    @Override
    public User createUser(UserRequest userRequest) {
        return Optional.of(userRequest).filter(user-> !userRepository.existsByEmail(userRequest.getEmail()))
                .map(req ->{
                    User user = new User();
                    user.setEmail(userRequest.getEmail());
                    user.setPassword(userRequest.getPassword());
                    user.setFirstName(userRequest.getFirstName());
                    user.setLastName(userRequest.getLastName());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new AlreadyExistsException("Opps! "+userRequest.getEmail()+" User Already Exists"));
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
}
