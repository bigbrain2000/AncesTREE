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
public class ElevationStatisticsClientProperties {

    @Value("${weatherbeaconboard.open-meteo.elevation.base-url}")
    private String elevationStatisticsBaseUrl;

    @Value("${weatherbeaconboard.open-meteo.elevation.elevation-url}")
    private String openMeteoElevationStatisticsUrl;

    @Bean("elevationStatisticsWebClient")
    public WebClient openMeteoWebCLient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl(elevationStatisticsBaseUrl)
                .build();
    }
}
