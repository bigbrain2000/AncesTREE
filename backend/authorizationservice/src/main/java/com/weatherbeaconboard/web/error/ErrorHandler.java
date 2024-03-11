package com.weatherbeaconboard.web.error;

import com.weatherbeaconboard.exceptions.UsersServiceExceptions;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class ErrorHandler {

    private static final String ERROR_MESSAGE = "Error resolving the request";

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>(e.getLocalizedMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(UsersServiceExceptions.class)
    public ResponseEntity<String> handleUsersServiceExceptions() {
        return new ResponseEntity<>(ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
    }

}
