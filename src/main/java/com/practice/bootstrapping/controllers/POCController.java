package com.practice.bootstrapping.controllers;

import com.practice.bootstrapping.dto.Message;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class POCController {

    @PostMapping("/poc/msg")
    public String revertGetJSON(@RequestBody Message message) {
        return message.toString();
    }

}
