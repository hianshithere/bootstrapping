package com.practice.bootstrapping.configurations.redis;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

import java.util.Objects;

@Configuration
public class BootstrapRedisConfig {

    private final RedisProperties redisProperties;

    public BootstrapRedisConfig(RedisProperties redisProperties) {
        this.redisProperties = Objects.requireNonNull(redisProperties, "redisProperties");
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration standalone = new RedisStandaloneConfiguration();
        String host = redisProperties.getHost() != null ? redisProperties.getHost() : "localhost";
        int port = redisProperties.getPort();
        if (port == 0) {
            port = 6379;
        }
        standalone.setHostName(host);
        standalone.setPort(port);
        return new JedisConnectionFactory(standalone);
    }

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory redisConnectionFactory,
                                                   MessageListenerAdapter messageListenerAdapter,
                                                   ChannelTopic topic) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        redisMessageListenerContainer.addMessageListener(messageListenerAdapter, topic);
        return redisMessageListenerContainer;
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter(RedisMessageSubscriber subscriber) {
        return new MessageListenerAdapter(subscriber, "onMessage");
    }

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic("bootstrap");
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setValueSerializer(new GenericToStringSerializer<>(Object.class));
        return redisTemplate;
    }

    @Bean
    public MessagePublisher messagePublisher(RedisTemplate<String, Object> redisTemplate, ChannelTopic topic) {
        return new RedisMessagePublisher(redisTemplate, topic);
    }
}
