package com.weatherbeaconboard.web.controller;

import com.weatherbeaconboard.controller.email.EmailService;
import com.weatherbeaconboard.web.model.EmailRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static com.weatherbeaconboard.utils.EmailRequestUtil.buildEmailRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(MockitoExtension.class)
class EmailControllerTest {

    @Mock
    private EmailService emailService;

    @InjectMocks
    private EmailController uut;

    @Test
    void sendEmail_Success() {
        doNothing().when(emailService).sendEmail(any(EmailRequest.class));
        final EmailRequest emailRequest = buildEmailRequest();

        final ResponseEntity<Void> voidResponseEntity = uut.sendEmail(emailRequest);

        assertEquals(OK, voidResponseEntity.getStatusCode());
        verify(emailService, times(1)).sendEmail(any(EmailRequest.class));
    }
}
