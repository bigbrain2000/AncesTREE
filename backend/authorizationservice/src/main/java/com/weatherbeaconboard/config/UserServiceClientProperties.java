package com.weatherbeaconboard.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Getter
@Setter
@Configuration
public class UserServiceClientProperties {

    @Value("${weatherbeaconboard.users.base-url}")
    private String userServiceBaseUrl;

    @Value("${weatherbeaconboard.users.userDetails}")
    private String userServiceUserDetailsUrl;

    @Bean("usersServiceWebClient")
    public WebClient userServiceWebCLient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl(userServiceBaseUrl)
                .build();
    }
}
