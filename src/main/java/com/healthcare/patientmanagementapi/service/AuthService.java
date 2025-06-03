package com.healthcare.patientmanagementapi.service;


import com.healthcare.patientmanagementapi.DTO.RegisterRequest;
import com.healthcare.patientmanagementapi.model.Role;
import com.healthcare.patientmanagementapi.model.User;
import com.healthcare.patientmanagementapi.repository.UserRepository;
import com.healthcare.patientmanagementapi.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login(String email, String password) {
        // 1. Authenticate email + password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        // 2. Load the user (we assume email is unique and used for login)
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found"); // could be a custom exception
        }

        User user = userOpt.get();

        // 3. Generate JWT token with email + role
        return jwtUtil.generateToken(user.getEmail(), user.getRole());
    }

    public User register(RegisterRequest request) {
        // 1. Check if user exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setName(request.getName());  // set name before saving
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));



        // Set role after validating
        try {
            Role role = Role.valueOf(request.getRole().toUpperCase());
            user.setRole(role);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + request.getRole());
        }

        // Save user to DB
        return userRepository.save(user);
    }
}
