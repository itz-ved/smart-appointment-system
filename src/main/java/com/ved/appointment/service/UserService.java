package com.ved.appointment.service;

import com.ved.appointment.entity.User;
import com.ved.appointment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public String registerUser(User user)
    {
        if(userRepository.existsByEmail(user.getEmail()))
        {
            return "Email is already registered";
        }
        userRepository.save(user);
        return "User registered successfully";
    }
    public String loginUser(String email,String password)
    {
        User user=userRepository.findByEmail(email)
                .orElse(null);
        if(user==null)
        {
            return "user not found";
        }
        if(!user.getPassword().equals(password))
        {
            return "Invalid password";
        }
        return "Login successful";
    }
}
