package com.practice.bootstrapping.repositories;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.practice.bootstrapping.entity.User;

@DataJpaTest
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	
	private User user;

	@BeforeEach
	void setup() {
		user = new User("ansh", "ansh", "ansh description");
		userRepository.save(user);
	}
	
	@Test
	void test_count() {
		long count = userRepository.count();
		System.out.println(count);
		assertTrue(count > 0, "Testing mock user count");
	}
	
}
