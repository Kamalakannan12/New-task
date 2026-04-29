package com.example.ecommerce.service;

import com.example.ecommerce.entity.UserDetails;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create user
    public UserDetails createUser(UserDetails user) {
        return userRepository.save(user);
    }

    // Get all users
    public List<UserDetails> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    public Optional<UserDetails> getUserById(Long id)
    {
        return userRepository.findById(id);

    }

    // Get user by email
    public Optional<UserDetails> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Delete user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
