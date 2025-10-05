package com.authorization.authorization.service;

import java.util.Date;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.authorization.authorization.entity.User;
import com.authorization.authorization.entity.models.UserRequest;
import com.authorization.authorization.entity.models.UserResponse;
import com.authorization.authorization.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse creatUser(UserRequest request) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        if (userRepository.findByPhoneNumber(request.phoneNumber()).isPresent()) {
            throw new RuntimeException("Phone number already registered");
        }

        User newUser = User.builder()
                .username(request.username())
                .email(request.email())
                .isActive(true)
                .fullName(request.fullName())
                .phoneNumber(request.phoneNumber())
                .createdAt(new Date())
                .password(passwordEncoder.encode(request.password()))
                .build();
        userRepository.save(newUser);

        return UserResponse.fromEntity(newUser);

    }
}
