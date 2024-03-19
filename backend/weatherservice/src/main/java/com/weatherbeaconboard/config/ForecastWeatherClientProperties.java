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
public class ForecastWeatherClientProperties {

    @Value("${weatherbeaconboard.open-meteo.forecast.base-url}")
    private String forecastWeatherBaseUrl;

    @Value("${weatherbeaconboard.open-meteo.forecast.forecast-url}")
    private String openMeteoForecastUrl;

    @Bean("forecastWeatherWebClient")
    public WebClient openMeteoWebCLient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl(forecastWeatherBaseUrl)
                .build();
    }
}
