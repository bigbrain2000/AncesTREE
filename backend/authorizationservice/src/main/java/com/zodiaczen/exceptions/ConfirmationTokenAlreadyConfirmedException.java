package com.zodiaczen.exceptions;

public class ConfirmationTokenAlreadyConfirmedException extends RuntimeException {

    public ConfirmationTokenAlreadyConfirmedException(String message) {
        super(message);
    }
}