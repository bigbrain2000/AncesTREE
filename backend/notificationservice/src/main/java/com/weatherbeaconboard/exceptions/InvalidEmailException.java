package com.weatherbeaconboard.exceptions;

/**
 * Exception thrown when an email is invalid.
 */
public class InvalidEmailException extends RuntimeException {

    public InvalidEmailException(String message) {
        super(String.format("Email %s is not valid!", message));
    }
}