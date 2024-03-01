package com.zodiaczen.exceptions;

import jakarta.mail.MessagingException;

/**
 * Exception thrown when there is a problem sending an email.
 */
public class EmailSendingException extends RuntimeException {

    public EmailSendingException(String message, MessagingException e) {
        super(message);
    }
}