package com.practice.bootstrapping.repositories;

import com.practice.bootstrapping.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserRepositoryTest {
	@Autowired
	private UserRepository userRepository;
	private User user;
	@BeforeEach
	void setup() {
		user = new User("ANSHIT", "ANSHIT", "ANSHIT DESCRIPTION");
		userRepository.save(user);
	}

	@Test
	void test_count() {
		long count = userRepository.count();
		assertTrue(count > 0, "Testing mock user count");
	}
	
}
