package com.weatherbeaconboard.exceptions;

/**
 * Exception thrown when trying to call the users service.
 */
public class UsersServiceExceptions extends RuntimeException {

    public UsersServiceExceptions(String message) {
        super(message);
    }
}