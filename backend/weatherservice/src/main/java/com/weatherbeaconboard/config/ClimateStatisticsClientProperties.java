package com.weatherbeaconboard.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Getter
@Configuration
public class ClimateStatisticsClientProperties {

    @Value("${weatherbeaconboard.open-meteo.climate.base-url}")
    private String climateStatisticsBaseUrl;

    @Value("${weatherbeaconboard.open-meteo.climate.climate-url}")
    private String openMeteoClimateStatisticsUrl;

    @Bean("climateStatisticsWebClient")
    public WebClient openMeteoWebCLient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl(climateStatisticsBaseUrl)
                .build();
    }
}
