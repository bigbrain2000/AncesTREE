package com.zodiaczen.web.error;

import com.zodiaczen.exceptions.*;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ErrorHandler {

    private static final String ERROR_MESSAGE = "Error resolving the request";

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Object> handleStudentNotFoundException() {
        return new ResponseEntity<>(ERROR_MESSAGE, NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>(e.getLocalizedMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<String> handleEntityAlreadyExistsException() {
        return new ResponseEntity<>(ERROR_MESSAGE, CONFLICT);
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<String> handleInvalidEmailException() {
        return new ResponseEntity<>("Provided email is not valid", BAD_REQUEST);
    }

    @ExceptionHandler(EmailSendingException.class)
    public ResponseEntity<String> handleEmailSendingException() {
        return new ResponseEntity<>(ERROR_MESSAGE, BAD_REQUEST);
    }

    @ExceptionHandler(ConversionException.class)
    public ResponseEntity<String> handleConversionException() {
        return new ResponseEntity<>(ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException() {
        return new ResponseEntity<>("Invalid username or password", BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleExpiredJwtException() {
        return new ResponseEntity<>("Token has expired", FORBIDDEN);
    }

    @ExceptionHandler(ConfirmationTokenExpiredException.class)
    public ResponseEntity<String> handleConfirmationTokenExpiredException() {
        return new ResponseEntity<>("Token expired", GONE);
    }

    @ExceptionHandler(ConfirmationTokenAlreadyConfirmedException.class)
    public ResponseEntity<String> handleConfirmationTokenAlreadyConfirmedException() {
        return new ResponseEntity<>("Token was already confirmed", CONFLICT);
    }

    @ExceptionHandler(InvalidPhoneNumberException.class)
    public ResponseEntity<String> handleInvalidPhoneNumberException() {
        return new ResponseEntity<>("Invalid phone number", BAD_REQUEST);
    }
}
