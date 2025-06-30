package com.example.myforum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.annotation.Rollback;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import com.example.myforum.repository.UserRepository;
import com.example.myforum.model.User;
import com.example.myforum.enums.UserRole;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repo;

    // test methods go below
    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("testemail@gmail.com");
        user.setPassword("password123");
        user.setEnabled(true);
        user.setRole(UserRole.USER);
        User savedUser = repo.save(user);
        User existUser = entityManager.find(User.class, savedUser.getUserId());
        assert existUser.getUsername().equals(user.getUsername());
        assert existUser.getEmail().equals(user.getEmail());
    }
}