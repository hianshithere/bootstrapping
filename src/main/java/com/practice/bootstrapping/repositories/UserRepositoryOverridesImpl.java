package com.practice.bootstrapping.repositories;

import com.practice.bootstrapping.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class UserRepositoryOverridesImpl implements UserRepositoryOverrides {
    public Iterable<User> findAll() {
        log.info("finalAll from abstract");
        return null;
    }
}
