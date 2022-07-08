package com.practice.bootstrapping.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.practice.bootstrapping.entity.User;
import com.practice.bootstrapping.repositories.UserRepository;

@SuppressWarnings({ "unused" })

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	UserRepository userRepository;

	@InjectMocks
	UserService service;

	@BeforeEach
	void setup() {
		System.out.println("UserService Test");
	}

	@Test
	void test_findAll() {
		List<User> users = new ArrayList<User>();

		users.add(new User("Amrita", "Shawarma", "Patni of Anshit Kumar Sharma"));
		users.add(new User("Amri", "Shawarma", "Patni of Anshit Kumar Sharma"));
		users.add(new User("Ammu", "Shawarma", "Patni of Anshit Kumar Sharma"));
		users.add(new User("Ammuia", "Shawarma", "Patni of Anshit Kumar Sharma"));
		users.add(new User("Babia", "Shawarma", "Patni of Anshit Kumar Sharma"));
		users.add(new User("Gauri", "Shawarma", "Patni of Anshit Kumar Sharma"));

		when(userRepository.findAll()).thenReturn(users);

		List<User> findAll = (List<User>) userRepository.findAll();

		assertTrue(findAll.size() > 0, "Find One mock");
	}

	@Test
	void test_findEverything() {

		List<User> users = new ArrayList<User>();

		users.add(new User("Amrita", "Shawarma", "Patni of Anshit Kumar Sharma"));

		// providing knowledge
		when(userRepository.findAll()).thenReturn(users);

		List<User> findAllUser = service.findAll();
		assertThat(findAllUser.size()).isGreaterThan(0);

	}

}
