package com.practice.bootstrapping.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.practice.bootstrapping.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>,UserRepositoryOverrides {

}
