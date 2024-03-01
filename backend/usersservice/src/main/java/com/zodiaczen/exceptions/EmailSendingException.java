package com.zodiaczen.exceptions;

/**
 * Exception thrown when there is a problem sending an email.
 */
public class EmailSendingException extends RuntimeException {

    public EmailSendingException(String message) {
        super(message);
    }
}