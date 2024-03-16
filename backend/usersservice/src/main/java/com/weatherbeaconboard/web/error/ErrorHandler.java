package com.weatherbeaconboard.web.error;

import com.weatherbeaconboard.exceptions.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
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
        return new ResponseEntity<>("The request contains invalid values", BAD_REQUEST);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<String> handleEntityAlreadyExistsException() {
        return new ResponseEntity<>(ERROR_MESSAGE, CONFLICT);
    }

    @ExceptionHandler(ConversionException.class)
    public ResponseEntity<String> handleConversionException() {
        return new ResponseEntity<>(ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
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

    @ExceptionHandler(NotificationServiceException.class)
    public ResponseEntity<String> handleNotificationServiceException() {
        return new ResponseEntity<>(ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
    }
}
