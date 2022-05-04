package com.practice.bootstrapping.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.practice.bootstrapping.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

}
