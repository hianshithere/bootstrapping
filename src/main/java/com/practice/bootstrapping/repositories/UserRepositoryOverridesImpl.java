package com.practice.bootstrapping.repositories;

import com.practice.bootstrapping.entity.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryOverridesImpl implements UserRepositoryOverrides {
    Log log = LogFactory.getLog (UserRepository.class);

    public Iterable<User> findAll() {
        log.info ("finalAll from abstract");
        return null;
    }
}
