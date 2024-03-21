package com.weatherbeaconboard.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Getter
@Configuration
public class AirQualityStatisticsClientProperties {

    @Value("${weatherbeaconboard.open-meteo.air-quality.base-url}")
    private String airQualityStatisticsBaseUrl;

    @Value("${weatherbeaconboard.open-meteo.air-quality.air-quality-url}")
    private String openMeteoAirQualityStatisticsUrl;

    @Bean("airQualityStatisticsWebClient")
    public WebClient openMeteoWebCLient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl(airQualityStatisticsBaseUrl)
                .build();
    }
}
