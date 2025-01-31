package com.Group6.fitness_tracker.controller;

import com.Group6.fitness_tracker.entity.Workout;
import com.Group6.fitness_tracker.entity.User;
import com.Group6.fitness_tracker.repository.WorkoutRepository;
import com.Group6.fitness_tracker.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Collections;
import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class WorkoutController {

    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;

    public WorkoutController(WorkoutRepository workoutRepository, UserRepository userRepository) {
        this.workoutRepository = workoutRepository;
        this.userRepository = userRepository;
    }

    // Get workouts by the authenticated user
    @GetMapping("/activities")
    public ResponseEntity<List<Workout>> getWorkoutsForAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Get the username from the SecurityContext

        // Find the user by username
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return ResponseEntity.status(404).body(Collections.emptyList());
        }

        // Find workouts for the found user
        List<Workout> workouts = workoutRepository.findByUser(user);

        if (workouts.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        return ResponseEntity.ok(workouts);
    }

    // Add a new workout
    @PostMapping("/logActivity")
    public ResponseEntity<Workout> logWorkout(@RequestBody Workout workout) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Get the username from the SecurityContext

        User user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(404).body(null); // User not found
        }

        workout.setUser(user);
        workout.setDate(LocalDate.now()); // Set the current date for the workout

        // Save the workout to the repository
        Workout savedWorkout = workoutRepository.save(workout);

        return ResponseEntity.ok(savedWorkout);
    }
}
