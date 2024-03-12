package com.weatherbeaconboard.service.email;

import com.weatherbeaconboard.client.NotificationServiceAsyncClient;
import com.weatherbeaconboard.web.model.EmailRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

    @Mock
    private NotificationServiceAsyncClient notificationServiceAsyncClient;

    @InjectMocks
    private EmailServiceImpl uut;

    @Test
    void sendRegistrationEmail_validEmailRequest_isSendingSuccessfullyTheEmail() {
        final EmailRequest emailRequest = EmailRequest.builder()
                .to("alex@yahoo.com")
                .subject("register")
                .body("body")
                .build();
        when(notificationServiceAsyncClient.sendRegistrationEmail(any(EmailRequest.class))).thenReturn(Mono.empty());

        uut.sendRegistrationEmail(emailRequest);

        verify(notificationServiceAsyncClient, times(1)).sendRegistrationEmail(any(EmailRequest.class));
    }

}