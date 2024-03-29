package com.practice.bootstrapping.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.bootstrapping.entity.User;
import com.practice.bootstrapping.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> saveAll(List<User> entities) {
        return userRepository.saveAll(entities);
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    public boolean existsById(Integer id) {
        return userRepository.existsById(id);
    }

    public List<User> findAllById(List<Integer> ids) {
        return userRepository.findAllById(ids);
    }

    public long count() {
        return userRepository.count();
    }

    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    public void delete(User entity) {
        userRepository.delete(entity);
    }

    public void deleteAllById(List<Integer> ids) {
        userRepository.deleteAllById(ids);
    }

    public void deleteAll(List<User> entities) {
        userRepository.deleteAll(entities);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public User findOne() {
        return new User();
    }

}
