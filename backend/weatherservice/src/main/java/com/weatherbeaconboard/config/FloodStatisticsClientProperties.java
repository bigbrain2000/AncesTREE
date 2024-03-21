package com.weatherbeaconboard.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Getter
@Configuration
public class FloodStatisticsClientProperties {

    @Value("${weatherbeaconboard.open-meteo.flood.base-url}")
    private String floodStatisticsBaseUrl;

    @Value("${weatherbeaconboard.open-meteo.flood.flood-url}")
    private String openMeteoFloodStatisticsUrl;

    @Bean("floodStatisticsWebClient")
    public WebClient openMeteoWebCLient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl(floodStatisticsBaseUrl)
                .build();
    }
}
