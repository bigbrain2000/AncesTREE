package com.weatherbeaconboard.exceptions;

public class NotificationServiceException extends RuntimeException {

    public NotificationServiceException(String message) {
        super(message);
    }
}