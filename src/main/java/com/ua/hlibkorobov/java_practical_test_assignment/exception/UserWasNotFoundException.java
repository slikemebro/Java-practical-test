package com.ua.hlibkorobov.java_practical_test_assignment.exception;

public class UserWasNotFoundException extends RuntimeException {
    public UserWasNotFoundException(String message) {
        super(message);
    }
}
