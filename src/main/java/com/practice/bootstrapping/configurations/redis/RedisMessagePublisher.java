package com.practice.bootstrapping.configurations.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
public class RedisMessagePublisher implements MessagePublisher {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic topic;

    public RedisMessagePublisher(RedisTemplate<String, Object> redisTemplate, ChannelTopic topic) {
        this.redisTemplate = redisTemplate;
        this.topic = topic;
    }

    @Override
    public void publish(String message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}