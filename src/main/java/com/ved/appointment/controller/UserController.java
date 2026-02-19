package com.ved.appointment.controller;

import com.ved.appointment.entity.User;
import com.ved.appointment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public String registerUser(@RequestBody User user)
    {
        return userService.registerUser(user);
    }
    @PostMapping("/login")
    public String loginUser(@RequestBody User user)
    {
        return userService.loginUser(user.getEmail(),user.getPassword());
    }

}
