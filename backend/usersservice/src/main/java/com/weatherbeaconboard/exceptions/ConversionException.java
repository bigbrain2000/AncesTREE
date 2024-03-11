package com.weatherbeaconboard.exceptions;

/**
 * Exception thrown when there is a problem in conversions.
 */
public class ConversionException extends RuntimeException {

    public ConversionException(String message) {
        super(message);
    }
}