package com.weatherbeaconboard.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Getter
@Configuration
public class NotificationServiceClientProperties {

    @Value("${weatherbeaconboard.notification.base-url}")
    private String notificationServiceBaseUrl;

    @Value("${weatherbeaconboard.notification.sendEmail}")
    private String notificationServiceSendEmailUrl;

    @Bean("notificationServiceWebClient")
    public WebClient userServiceWebCLient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl(notificationServiceBaseUrl)
                .build();
    }
}
