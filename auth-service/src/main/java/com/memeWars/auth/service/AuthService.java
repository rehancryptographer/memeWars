package com.memeWars.auth.service;

import com.memeWars.auth.model.User;
import com.memeWars.auth.repository.UserRepository;
import com.memeWars.auth.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Register User with encrypted password
    public String registerUser(String username, String email, String password) {
        // Check if the email is already in use
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already in use");
        }
        // Check if the username is already taken
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already taken");
        }

        // Create new user and set properties
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.getRoles().add("USER");  // Add default role

        // Save user to the repository
        userRepository.save(newUser);

        // Generate and return JWT token
        return jwtUtil.generateToken(username);
    }

    // Login User and validate password
    public String loginUser(String username, String password) {
        // Fetch user by username
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Invalid username or password");
        }

        User user = userOpt.get();
        // Check if password matches
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        // Generate and return JWT token for valid login
        return jwtUtil.generateToken(username);
    }

    // Validate JWT Token (New method to verify tokens)
    public boolean validateToken(String token, String username) {
        return jwtUtil.validateToken(token, username);
    }
}
