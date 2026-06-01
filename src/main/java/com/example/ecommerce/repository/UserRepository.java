package com.example.ecommerce.repository;

import com.example.ecommerce.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserDetails, Long> {

    Optional<UserDetails> findByEmail(String email);
    Optional<UserDetails> findByname(String username);
}
