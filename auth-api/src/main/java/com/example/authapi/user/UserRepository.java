package com.example.authapi.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

// Provides database operations for the User entity.
public interface UserRepository extends JpaRepository<User, UUID> {

    // Finds a user by email. Used during authentication.
    Optional<User> findByEmail(String email);

    // Checks whether an email is already registered.
    boolean existsByEmail(String email);
}