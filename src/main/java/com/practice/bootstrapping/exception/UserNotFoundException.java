package com.practice.bootstrapping.exception;

public class UserNotFoundException extends InvalidDataRequestException implements UserException {

    public UserNotFoundException(String invalidUserIsBeingAsked, Throwable exception) {
        super(invalidUserIsBeingAsked, exception);
    }

    public UserNotFoundException(String invalidUserIsBeingAsked) {
        super(invalidUserIsBeingAsked);
    }

}
