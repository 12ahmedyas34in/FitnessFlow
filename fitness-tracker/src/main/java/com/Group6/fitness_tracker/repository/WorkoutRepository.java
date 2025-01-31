package com.Group6.fitness_tracker.repository;

import com.Group6.fitness_tracker.entity.Workout;
import com.Group6.fitness_tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    List<Workout> findByUser(User user); // Find workouts by associated User
}

