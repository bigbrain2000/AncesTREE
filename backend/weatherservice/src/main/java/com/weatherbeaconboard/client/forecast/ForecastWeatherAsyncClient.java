package com.weatherbeaconboard.client.forecast;

import com.weatherbeaconboard.config.ForecastWeatherClientProperties;
import com.weatherbeaconboard.exceptions.OpenMeteoException;
import com.weatherbeaconboard.web.model.forecast.ForecastWeatherRequest;
import com.weatherbeaconboard.web.model.forecast.ForecastWeatherResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Validated
@Component
public class ForecastWeatherAsyncClient {

    private final WebClient webClient;
    private final ForecastWeatherClientProperties forecastWeatherClientProperties;

    public ForecastWeatherAsyncClient(@Qualifier("forecastWeatherWebClient") WebClient webClient,
                                      ForecastWeatherClientProperties forecastWeatherClientProperties) {

        this.webClient = webClient;
        this.forecastWeatherClientProperties = forecastWeatherClientProperties;
    }

    public Mono<ForecastWeatherResponse> getForecastWeather(@NotNull @Valid ForecastWeatherRequest request) {
        log.debug("Calling OpenMeteo API to retrieve the forecast");

        final String hourlyParams = String.join(",", request.hourly());
        final String dailyParams = String.join(",", request.daily());

        final Function<UriBuilder, URI> uriBuilderURIFunction = uriBuilder -> uriBuilder.path(forecastWeatherClientProperties.getOpenMeteoForecastUrl())
                .queryParam("latitude", request.latitude())
                .queryParam("longitude", request.longitude())
                .queryParamIfPresent("elevation", Optional.ofNullable(request.elevation()))
                .queryParamIfPresent("hourly", Optional.of(hourlyParams))
                .queryParamIfPresent("daily", Optional.of(dailyParams))
                .queryParamIfPresent("current", Optional.ofNullable(request.current()))
                .queryParamIfPresent("temperature_unit", Optional.ofNullable(request.temperatureUnit()))
                .queryParamIfPresent("wind_speed_unit", Optional.ofNullable(request.windSpeedUnit()))
                .queryParamIfPresent("precipitation_unit", Optional.ofNullable(request.precipitationUnit()))
                .queryParamIfPresent("timeformat", Optional.ofNullable(request.timeFormat()))
                .queryParamIfPresent("timezone", Optional.ofNullable(request.timezone()))
                .queryParamIfPresent("past_days", Optional.ofNullable(request.pastDays()))
                .queryParamIfPresent("forecast_days", Optional.ofNullable(request.forecastDays()))
                .queryParamIfPresent("start_date", Optional.ofNullable(request.startDate()))
                .queryParamIfPresent("end_date", Optional.ofNullable(request.endDate()))
                .queryParamIfPresent("start_hour", Optional.ofNullable(request.startHour()))
                .queryParamIfPresent("end_hour", Optional.ofNullable(request.endHour()))
                .queryParamIfPresent("start_minutely_15", Optional.ofNullable(request.startMinutely15()))
                .queryParamIfPresent("end_minutely_15", Optional.ofNullable(request.endMinutely15()))
                .queryParamIfPresent("models", Optional.ofNullable(request.models()))
                .queryParamIfPresent("cell_selection", Optional.ofNullable(request.cellSelection()))
                .build();

        return webClient.get()
                .uri(uriBuilderURIFunction)
                .retrieve()
                .bodyToMono(ForecastWeatherResponse.class)
                .onErrorResume(error -> {
                    log.error("Error occurred while calling the OpenMeteo API for getting the forecast weather {}", error.getMessage());
                    return Mono.error(new OpenMeteoException("Error calling the OpenMeteo API for getting the forecast weather"));
                });
    }
}