package com.practice.bootstrapping.repositories;

import com.practice.bootstrapping.entity.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public abstract class UserRepositoryOverrides implements UserRepository{
    @Override
    public Iterable<User> findAll() {
       ArrayList<User> list = new ArrayList();
       list.add(new User());
       return list;
    }
}
