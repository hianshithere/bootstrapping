package com.practice.bootstrapping.controllers;

import com.practice.bootstrapping.configurations.RedisMessagePublisher;
import com.practice.bootstrapping.configurations.RedisMessageSubscriber;
import com.practice.bootstrapping.dto.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/redis")
public class RedisController {

    @Autowired
    private RedisMessagePublisher messagePublisher;


    @PostMapping("/publish")
    public void setMessage(@RequestBody Message message) {
        log.info(">> publishing : {}", message);
        messagePublisher.publish(message.toString());
    }

    @GetMapping("/subscribe")
    public List<String> getMessages() {
        return RedisMessageSubscriber.messageList;
    }

}