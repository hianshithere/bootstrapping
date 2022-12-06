package com.practice.bootstrapping.repositories;

import com.practice.bootstrapping.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryOverrides {

}
