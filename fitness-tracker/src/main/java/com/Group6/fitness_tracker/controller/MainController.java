package com.Group6.fitness_tracker.controller;

import com.Group6.fitness_tracker.entity.User;
import com.Group6.fitness_tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            User user = userService.getUserByUsername(auth.getName());
            model.addAttribute("user", user);
            return "index";
        }
        return "redirect:/login";
    }
}
