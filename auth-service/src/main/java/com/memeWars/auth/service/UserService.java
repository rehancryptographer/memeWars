package com.memeWars.auth.service;

import com.memeWars.auth.model.User;
import com.memeWars.auth.repository.UserRepository;
import com.memeWars.auth.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    // Fetch User Profile by Token
    public User getUserProfile(String token) {
        // Extract username from JWT token
        String username = jwtUtil.extractUsername(token.substring(7)); // Remove "Bearer " prefix

        // Retrieve user from database
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Update User Profile
    public User updateUserProfile(String token, User updatedUser) {
        // Extract username from JWT token
        String username = jwtUtil.extractUsername(token.substring(7)); // Remove "Bearer " prefix

        // Retrieve user from database
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update profile fields (avatar, bio, meme stats)
        if (updatedUser.getAvatar() != null) user.setAvatar(updatedUser.getAvatar());
        if (updatedUser.getBio() != null) user.setBio(updatedUser.getBio());
        if (updatedUser.getMemeStats() != null) user.setMemeStats(updatedUser.getMemeStats());

        // Save updated user profile to the database
        return userRepository.save(user);
    }

    // Add more profile-related functionality if needed (e.g., change password, add/remove roles)
}
