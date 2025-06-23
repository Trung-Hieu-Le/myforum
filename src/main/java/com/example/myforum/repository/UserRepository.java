package com.example.myforum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.example.myforum.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameAndEmail(String username, String email);
    boolean existsByUsername(String setUsername);
}
