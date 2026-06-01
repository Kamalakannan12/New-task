package com.example.ecommerce.configuration;

import com.example.ecommerce.entity.UserDetails;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class Processor implements ItemProcessor<UserDetails, UserDetails> {

    private final UserRepository userRepository;

    public Processor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails process(UserDetails incomingUser) {
        System.out.println("PROCESSING : " + incomingUser.getEmail());

        incomingUser.setLastactive(LocalDate.now());

        return userRepository.findByEmail(incomingUser.getEmail())
                .map(existingUser -> {

                    // UPDATE EXISTING USER

                    existingUser.setName(incomingUser.getName());
                    existingUser.setAddress(incomingUser.getAddress());
                    existingUser.setPhone(incomingUser.getPhone());
                    existingUser.setPassword(incomingUser.getPassword());
                    existingUser.setLastactive(LocalDate.now());

                    System.out.println("UPDATED USER : " + existingUser.getEmail());

                    return userRepository.save(existingUser);                })
                .orElseGet(() -> {

                    // INSERT NEW USER

                    System.out.println("NEW USER : " + incomingUser.getEmail());

                    return userRepository.save(incomingUser);
                });
    }
}