package com.weatherbeaconboard.service;

import com.weatherbeaconboard.client.NotificationServiceAsyncClient;
import com.weatherbeaconboard.config.NotificationServiceClientProperties;
import com.weatherbeaconboard.exceptions.NotificationServiceException;
import com.weatherbeaconboard.web.model.EmailRequest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.io.IOException;

class NotificationServiceAsyncClientTest {

    private MockWebServer mockWebServer;
    private NotificationServiceAsyncClient notificationServiceAsyncClient;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        final WebClient webClient = WebClient.builder()
                .baseUrl(mockWebServer.url("/v1").toString())
                .build();

        final NotificationServiceClientProperties notificationServiceClientProperties = new NotificationServiceClientProperties();
        notificationServiceClientProperties.setNotificationServiceSendEmailUrl(mockWebServer.url("/sendEmail").toString());

        notificationServiceAsyncClient = new NotificationServiceAsyncClient(webClient, notificationServiceClientProperties);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void sendRegistrationEmail_Success() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE));

        final EmailRequest emailRequest = new EmailRequest("to@example.com", "Subject", "Body");

        StepVerifier.create(notificationServiceAsyncClient.sendRegistrationEmail(emailRequest))
                .verifyComplete();
    }

    @Test
    void sendRegistrationEmail_Error() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(500));

        final EmailRequest emailRequest = new EmailRequest("to@example.com", "Subject", "Body");

        StepVerifier.create(notificationServiceAsyncClient.sendRegistrationEmail(emailRequest))
                .verifyError(NotificationServiceException.class);
    }
}
