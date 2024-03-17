package com.weatherbeaconboard.service.client;

import com.weatherbeaconboard.client.UserServiceAsyncClient;
import com.weatherbeaconboard.config.UserServiceClientProperties;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.io.IOException;

public class UserServiceAsyncClientServiceTests {

    private MockWebServer mockWebServer;
    private UserServiceAsyncClient userServiceAsyncClient;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        final WebClient webClient = WebClient.builder()
                .baseUrl(mockWebServer.url("/v1").toString())
                .build();

        final UserServiceClientProperties userServiceClientProperties = new UserServiceClientProperties();
        userServiceClientProperties.setUserServiceUserDetailsUrl(mockWebServer.url("/v1/userDetails").toString());

        userServiceAsyncClient = new UserServiceAsyncClient(webClient, userServiceClientProperties);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void getUserDetails_returnsSuccess() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE));

        StepVerifier.create(userServiceAsyncClient.getUserDetails("test"))
                .verifyComplete();
    }
}
