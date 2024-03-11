package com.weatherbeaconboard.exceptions;

public class ConfirmationTokenAlreadyConfirmedException extends RuntimeException {

    public ConfirmationTokenAlreadyConfirmedException(String message) {
        super(message);
    }
}