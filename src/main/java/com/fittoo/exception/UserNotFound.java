package com.fittoo.exception;

public class UserNotFound extends RuntimeException{

    public UserNotFound(String error) {
        super(error);
    }
}
