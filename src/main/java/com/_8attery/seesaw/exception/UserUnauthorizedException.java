package com._8attery.seesaw.exception;

public class UserUnauthorizedException extends RuntimeException {
    public UserUnauthorizedException(String message) {
        super(message);
    }
}
