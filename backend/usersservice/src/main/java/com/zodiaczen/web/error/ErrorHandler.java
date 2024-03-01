package com.zodiaczen.web.error;

import com.zodiaczen.exceptions.ConversionException;
import com.zodiaczen.exceptions.EmailSendingException;
import com.zodiaczen.exceptions.EntityAlreadyExistsException;
import com.zodiaczen.exceptions.InvalidEmailException;
import jakarta.persistence.EntityNotFoundException;
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

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException() {
        return new ResponseEntity<>(ERROR_MESSAGE, NOT_FOUND);
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
}
