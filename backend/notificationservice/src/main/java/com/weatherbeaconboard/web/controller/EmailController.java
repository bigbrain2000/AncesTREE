package com.weatherbeaconboard.web.controller;

import com.weatherbeaconboard.controller.email.EmailService;
import com.weatherbeaconboard.web.model.EmailRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @PostMapping("/sendEmail")
    public ResponseEntity<Void> sendEmail(@RequestBody @NotNull @Valid EmailRequest emailRequest) {

        log.info("Initiate sending registration email");
        emailService.sendEmail(emailRequest);

        return new ResponseEntity<>(OK);
    }
}
