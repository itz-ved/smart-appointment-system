package com.ved.appointment.service;

import com.ved.appointment.entity.User;
import com.ved.appointment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public Map<String, Object> registerUser(User user)
    {
        // ✅ Validation start
        if(user.getName() == null || user.getName().isEmpty()) {
            return Map.of("message", "Name is required");
        }

        if(user.getEmail() == null || user.getEmail().isEmpty()) {
            return Map.of("message", "Email is required");
        }

        if(user.getPassword() == null || user.getPassword().isEmpty()) {
            return Map.of("message", "Password is required");
        }

        // ✅ Existing logic
        if(userRepository.existsByEmail(user.getEmail()))
        {
            return Map.of("message", "Email already registered");
        }

        User savedUser = userRepository.save(user);

        return Map.of(
                "message", "User registered successfully",
                "userId", savedUser.getId(),
                "name", savedUser.getName()
        );
    }
    public Map<String, Object> loginUser(String email, String password)
    {
        // ✅ Validation
        if(email == null || email.isEmpty()) {
            return Map.of("message", "Email is required");
        }

        if(password == null || password.isEmpty()) {
            return Map.of("message", "Password is required");
        }

        User user = userRepository.findByEmail(email)
                .orElse(null);

        if(user == null) {
            return Map.of("message", "User not found");
        }

        if(!user.getPassword().equals(password)) {
            return Map.of("message", "Invalid password");
        }

        return Map.of(
                "message", "Login successful",
                "userId", user.getId(),
                "name", user.getName()
        );
    }
}
