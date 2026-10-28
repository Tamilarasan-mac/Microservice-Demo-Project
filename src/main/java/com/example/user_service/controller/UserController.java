package com.demo.userservice.controller;

import com.demo.userservice.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    // Fake in-memory data (no database needed for demo)
    private List<User> users = Arrays.asList(
            new User(1L, "Tamil King", "tamil@gmail.com"),
            new User(2L, "Tamilarasan", "arasan@gmail.com"),
            new User(3L, "King", "king@gmail.com")
    );

    @GetMapping
    public List<User> getAllUsers() {
        return users;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
    }

    @GetMapping("/health")
    public String health() {
        return "User Service is UP!";
    }
}