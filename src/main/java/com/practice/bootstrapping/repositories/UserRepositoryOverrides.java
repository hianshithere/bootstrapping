package com.practice.bootstrapping.repositories;

import com.practice.bootstrapping.entity.User;

interface UserRepositoryOverrides {
    Iterable<User> findAll();
}
