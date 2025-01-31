package com.Group6.fitness_tracker.controller;

import com.Group6.fitness_tracker.entity.User;
import com.Group6.fitness_tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String name,
            @RequestParam int age,
            @RequestParam double height,
            @RequestParam double weight,
            Model model) {

        try {
            // Check if username already exists
            if (userService.existsByUsername(username)) {
                return "redirect:/register?error=username";
            }

            // Check if email already exists
            if (userService.existsByEmail(email)) {
                return "redirect:/register?error=email";
            }

            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setName(name);
            user.setAge(age);
            user.setHeight(height);
            user.setWeight(weight);

            userService.saveUser(user);
            return "redirect:/login?registered";
        } catch (Exception e) {
            return "redirect:/register?error";
        }
    }
}