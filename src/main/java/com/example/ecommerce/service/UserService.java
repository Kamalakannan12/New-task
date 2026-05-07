package com.example.ecommerce.service;

import com.example.ecommerce.entity.UserDetails;
import com.example.ecommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create user
    public UserDetails createUser(UserDetails user) {
        user.setLastactive(LocalDateTime.now());

        return userRepository.save(user);
    }

    // Get all users
    public List<UserDetails> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    @Transactional
    public Optional<UserDetails> getUserById(Long id)
    {
        return userRepository.findById(id);

    }

    // Get user by email
    public Optional<UserDetails> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    //check status
    public List<String> getUserStatus(int days) {

        List<UserDetails> users = userRepository.findAll();
        List<String> result = new ArrayList<>();

        LocalDateTime cutoff = LocalDateTime.now().minusDays(days);

        for (UserDetails user : users) {

            String status;

            if (user.getLastactive().isBefore(cutoff)) {
                status = "INACTIVE";
            } else {
                status = "ACTIVE";
            }

            result.add(user.getEmail() + " - " + status);
        }

        return result;
    }
    public List<UserDetails> getInactiveUsers(int days) {

        List<UserDetails> users = userRepository.findAll();
        List<UserDetails> inactiveUsers = new ArrayList<>();

        LocalDateTime cutoff = LocalDateTime.now().minusDays(days);

        for (UserDetails user : users) {

            if (user.getLastactive() != null &&
                    user.getLastactive().isBefore(cutoff)) {

                inactiveUsers.add(user);
            }
        }

        return inactiveUsers;
    }
    // Delete user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
