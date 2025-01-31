package com.Group6.fitness_tracker.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.Group6.fitness_tracker.entity.User;
import com.Group6.fitness_tracker.repository.UserRepository;

@Configuration
public class DataInitializationConfig {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Check if a user with the username "admin" exists
            User admin = userRepository.findByUsername("admin");
            if (admin == null) {  // If the user doesn't exist, create one
                admin = new User();
                admin.setUsername("admin");
                admin.setEmail("admin@gmail.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setName("Admin-User");
                admin.setAge(20);
                admin.setHeight(175);
                admin.setWeight(70);
                userRepository.save(admin);  // Save the admin user
            }
        };
    }
}

