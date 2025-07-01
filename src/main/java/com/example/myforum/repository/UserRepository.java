package com.example.myforum.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myforum.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);

    Optional<User> findByUsernameAndEmail(String username, String email);

    boolean existsByUsername(String setUsername);
}
