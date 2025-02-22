package com.practice.bootstrapping.services;

import com.practice.bootstrapping.dto.UserDTO;
import com.practice.bootstrapping.entity.User;
import com.practice.bootstrapping.exception.UserNotFoundException;
import com.practice.bootstrapping.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.practice.bootstrapping.helper.UserMapper.mapUserEntityToUserDTO;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private void validate(Optional<User> currentUser) {
        currentUser.stream()
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("user not found"));
    }

    public UserDTO retrieveValidUserBy(int id) {

        Optional<User> requestedUser = userRepository.findById(id);
        validate(requestedUser);
        return mapUserEntityToUserDTO(requestedUser.get());

    }

    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }


}
