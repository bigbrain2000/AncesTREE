package com.weatherbeaconboard.web.error;

import com.weatherbeaconboard.exceptions.OpenMeteoException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class ErrorHandler {

    private static final String ERROR_MESSAGE = "Error resolving the request";

    @ExceptionHandler(OpenMeteoException.class)
    public ResponseEntity<String> handleOpenMeteoException(OpenMeteoException e) {
        return new ResponseEntity<>(e.getMessage(), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException() {
        return new ResponseEntity<>("The request is invalid.", BAD_REQUEST);
    }

}
