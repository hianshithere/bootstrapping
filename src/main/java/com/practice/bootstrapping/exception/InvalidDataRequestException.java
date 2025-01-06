package com.practice.bootstrapping.exception;

public class InvalidDataRequestException extends RuntimeException {
    public InvalidDataRequestException(String invalidUserIsBeingAsked) {
        super(invalidUserIsBeingAsked);
    }

    public InvalidDataRequestException(String invalidUserIsBeingAsked, Throwable exception) {
        super(invalidUserIsBeingAsked, exception);
    }
}
