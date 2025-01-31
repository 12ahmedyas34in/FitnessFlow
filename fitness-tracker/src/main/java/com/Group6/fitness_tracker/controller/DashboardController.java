package com.Group6.fitness_tracker.controller;

import com.Group6.fitness_tracker.entity.User;
import com.Group6.fitness_tracker.entity.Workout;
import com.Group6.fitness_tracker.repository.UserRepository;
import com.Group6.fitness_tracker.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DashboardController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkoutRepository workoutRepository;

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<Workout> workouts = workoutRepository.findByUser(user);

        int totalCalories = 0;
        double totalDistance = 0;

        for (Workout workout : workouts) {
            totalCalories += workout.getCalories();
            totalDistance += workout.getDistance();
        }

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalCalories", totalCalories);
        stats.put("totalDistance", totalDistance);

        return ResponseEntity.ok(stats);
    }
}
