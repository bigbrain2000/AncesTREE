package com.weatherbeaconboard.exceptions;

public class EntityIsNotExistingException extends RuntimeException {

    public EntityIsNotExistingException(String message) {
        super(message);
    }
}