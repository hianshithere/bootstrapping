package com.practice.bootstrapping.services;

import com.practice.bootstrapping.entity.User;
import com.practice.bootstrapping.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SuppressWarnings({ "unused" })

@ExtendWith(MockitoExtension.class)
@Slf4j
class UserServiceTest {

	@Mock
	UserRepository userRepository;
	@InjectMocks
	UserService service;

	@BeforeEach
	void setup() {
		log.info("setup....");
	}

	@Test
	void test_findAll() {
		List<User> users = new ArrayList<User>();
		{
			users.add(new User("Amrita", "Shawarma", "Patni of Anshit Kumar Sharma"));
			users.add(new User("Amri", "Shawarma", "Patni of Anshit Kumar Sharma"));
			users.add(new User("Ammu", "Shawarma", "Patni of Anshit Kumar Sharma"));
			users.add(new User("Ammuia", "Shawarma", "Patni of Anshit Kumar Sharma"));
			users.add(new User("Babia", "Shawarma", "Patni of Anshit Kumar Sharma"));
			users.add(new User("Gauri", "Shawarma", "Patni of Anshit Kumar Sharma"));
		}

		when(userRepository.findAll()).thenReturn(users);
		List<User> findAll = (List<User>) userRepository.findAll();
		assertTrue(findAll.size() > 0, "Find One mock");
	}

	@Test
	void test_findEverything() {

		// data setup
		List<User> users = new ArrayList<User>();
		users.add(new User(
				"Amrita",
				"Shawarma",
				"Patni of Anshit Kumar Sharma")
		);

		// providing knowledge
		when(userRepository.findAll()).thenReturn(users);
		List<User> findAllUser = service.findAll();
		assertThat(findAllUser.size()).isGreaterThan(0);
	}

}
