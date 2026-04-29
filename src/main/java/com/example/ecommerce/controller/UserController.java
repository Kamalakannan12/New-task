package com.example.ecommerce.controller;

import com.example.ecommerce.entity.UserDetails;
import com.example.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Create user
    @PostMapping
    public UserDetails createUser(@RequestBody UserDetails user) {
        return userService.createUser(user);
    }

    // Get all users
    @GetMapping
    public List<UserDetails> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get user by ID
    @GetMapping("/{id}")
    public UserDetails getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Delete user
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }
}
