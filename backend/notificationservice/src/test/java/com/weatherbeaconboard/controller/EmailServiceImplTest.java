package com.weatherbeaconboard.controller;

import com.weatherbeaconboard.config.EmailProperties;
import com.weatherbeaconboard.controller.email.EmailServiceImpl;
import com.weatherbeaconboard.web.model.EmailRequest;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private EmailProperties emailProperties;

    @Mock
    private MimeMessage mimeMessage;

    @InjectMocks
    private EmailServiceImpl uut;

    @Test
    void sendEmail_validInputs_isSuccess() {
        when(emailProperties.getApplicationEmail()).thenReturn("weatherbeaconboard@gmail.com");
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        doNothing().when(mailSender).send(mimeMessage);

        final EmailRequest emailRequest = EmailRequest.builder()
                .to("alex@yahoo.com")
                .subject("register")
                .body("body")
                .build();

        uut.sendEmail(emailRequest);

        verify(mailSender, times(1)).send(mimeMessage);
    }
}