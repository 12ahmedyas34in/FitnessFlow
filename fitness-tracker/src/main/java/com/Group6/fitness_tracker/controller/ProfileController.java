package com.Group6.fitness_tracker.controller;

import com.Group6.fitness_tracker.entity.User;
import com.Group6.fitness_tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    // GET endpoint to display the profile page
    @GetMapping("/profile")
    public String showProfileForm(Model model) {
        // Fetch the current authenticated user using SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Retrieve user details from the database
        User user = userService.getUserByUsername(username);
        model.addAttribute("user", user);  // Add the user to the model for the profile form

        return "profile";  // Return the profile template (profile.html)
    }

    // POST endpoint to save the updated profile data
    @PostMapping("/saveProfile")
    public String saveProfile(
            @RequestParam("email") String email,
            @RequestParam("name") String name,
            @RequestParam("age") int age,
            @RequestParam("height") double height,
            @RequestParam("weight") double weight) {

        // Fetch the current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Retrieve the current user and update the fields
        User user = userService.getUserByUsername(username);
        user.setEmail(email);
        user.setName(name);
        user.setAge(age);
        user.setHeight(height);
        user.setWeight(weight);

        // Save the updated user object to the database
        userService.saveUser(user);

        return "redirect:/profile?success";  // Redirect to profile page with a success message
    }
}
