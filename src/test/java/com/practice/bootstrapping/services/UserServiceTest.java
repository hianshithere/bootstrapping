package com.practice.bootstrapping.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.practice.bootstrapping.entity.User;
import com.practice.bootstrapping.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService service;

	@Test
	void findAll_whenRepositoryHasUsers_returnsNonEmptyList() {
		List<User> users = Arrays.asList(
				new User("Amrita", "Shawarma", "Patni of Anshit Kumar Sharma"),
				new User("Amri", "Shawarma", "Patni of Anshit Kumar Sharma"),
				new User("Ammu", "Shawarma", "Patni of Anshit Kumar Sharma"));

		when(userRepository.findAll()).thenReturn(users);
		List<User> result = service.findAll();
		assertThat(result).isNotEmpty();
		assertThat(result).hasSize(users.size());
	}

	@Test
	void findAll_singleUser_returnListContainingThatUser() {
		List<User> users = Arrays.asList(
				new User("Amrita", "Shawarma", "Patni of Anshit Kumar Sharma"));
		when(userRepository.findAll()).thenReturn(users);
		List<User> result = service.findAll();
		assertThat(result).containsExactlyElementsOf(users);
	}

}
