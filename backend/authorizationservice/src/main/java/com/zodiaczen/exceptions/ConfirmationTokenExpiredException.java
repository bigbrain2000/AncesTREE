package com.zodiaczen.exceptions;

public class ConfirmationTokenExpiredException extends RuntimeException {

    public ConfirmationTokenExpiredException(String message) {
        super(message);
    }
}