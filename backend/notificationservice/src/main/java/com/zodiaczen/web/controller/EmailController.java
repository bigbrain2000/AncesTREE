package com.zodiaczen.web.controller;

import com.zodiaczen.service.EmailService;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Validated
@AllArgsConstructor
@RestController
@RequestMapping(value = "/v1", produces = APPLICATION_JSON_VALUE)
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/sendRegistrationEmail")
    public ResponseEntity<Void> sendEmail(@RequestParam @NotBlank String to,
                                          @RequestParam @NotBlank String subject,
                                          @RequestParam @NotBlank String body) {

        log.info("Initiate sending registration email");
        emailService.sendEmail(to, subject, body);
        return new ResponseEntity<>(OK);
    }

    @PostMapping("/validateEmail")
    public ResponseEntity<Void> validateEmail(@RequestParam @NotBlank String email) {
        log.info("Initiate validating the user email");
        emailService.checkIfEmailIsValid(email);
        return new ResponseEntity<>(OK);
    }
}
