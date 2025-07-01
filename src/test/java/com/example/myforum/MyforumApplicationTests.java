package com.example.myforum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.example.myforum.model.User;
import com.example.myforum.enums.UserRole;
import com.example.myforum.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(true)
class MyforumApplicationTests {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	void testCreateUser() {
		User user = new User();
		user.setUsername("testuser");
		user.setPassword("password123");
		user.setEmail("myemail@gmail.com");
		user.setEnabled(true);
		user.setRole(UserRole.USER);
		
		User savedUser = userRepository.save(user);
		User existUser = testEntityManager.find(User.class, savedUser.getUserId());
		assert existUser.getUsername().equals(user.getUsername());
	}	

}
