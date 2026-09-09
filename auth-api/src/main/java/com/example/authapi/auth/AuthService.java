package com.example.authapi.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.authapi.auth.dto.RegisterRequest;
import com.example.authapi.user.User;
import com.example.authapi.user.UserRepository;

// Handles authentication-related business logic.
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Registers a new user and stores only the BCrypt password hash.
    public void register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        String passwordHash = passwordEncoder.encode(request.password());

        User user = new User(request.email(), passwordHash);

        userRepository.save(user);
    }
}