package com.practice.bootstrapping.configurations.redis;

public interface MessagePublisher {
    void publish(String message);

    // TODO: hashOps() for redis
}