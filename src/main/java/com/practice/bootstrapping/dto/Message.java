package com.practice.bootstrapping.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String data;
    private String author;

    @Override
    public String toString() {
        return "Message{" +
                "data='" + data + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}