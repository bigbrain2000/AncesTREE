package com.weatherbeaconboard.exceptions;

public class ConfirmationTokenExpiredException extends RuntimeException {

    public ConfirmationTokenExpiredException(String message) {
        super(message);
    }
}