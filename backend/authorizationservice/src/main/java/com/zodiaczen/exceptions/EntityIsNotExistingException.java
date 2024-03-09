package com.zodiaczen.exceptions;

public class EntityIsNotExistingException extends RuntimeException {

    public EntityIsNotExistingException(String message) {
        super(message);
    }
}