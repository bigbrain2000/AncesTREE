package com.weatherbeaconboard.client.climate;

import com.weatherbeaconboard.config.ClimateStatisticsClientProperties;
import com.weatherbeaconboard.exceptions.OpenMeteoException;
import com.weatherbeaconboard.web.model.climatechange.ClimateChangeRequest;
import com.weatherbeaconboard.web.model.climatechange.ClimateChangeRespose;
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
public class ClimateStatisticsAsyncClient {

    private final WebClient webClient;
    private final ClimateStatisticsClientProperties climateStatisticsClientProperties;

    public ClimateStatisticsAsyncClient(@Qualifier("climateStatisticsWebClient") WebClient webClient,
                                        ClimateStatisticsClientProperties climateStatisticsClientProperties) {

        this.webClient = webClient;
        this.climateStatisticsClientProperties = climateStatisticsClientProperties;
    }

    public Mono<ClimateChangeRespose> getClimateStatistics(@NotNull @Valid ClimateChangeRequest request) {
        log.debug("Calling OpenMeteo API to retrieve the climate statistics");


        final Function<UriBuilder, URI> uriBuilderURIFunction = uriBuilder -> uriBuilder
                .path(climateStatisticsClientProperties.getOpenMeteoClimateStatisticsUrl())
                .queryParam("latitude", request.latitude())
                .queryParam("longitude", request.longitude())
                .queryParam("start_date", request.startDate().toString())
                .queryParam("end_date", request.endDate().toString())
                .queryParam("models", String.join(",", request.models()))
                .queryParam("daily", String.join(",", request.daily()))
                .queryParamIfPresent("temperature_unit", Optional.ofNullable(request.temperatureUnit()))
                .queryParamIfPresent("wind_speed_unit", Optional.ofNullable(request.windSpeedUnit()))
                .queryParamIfPresent("precipitation_unit", Optional.ofNullable(request.precipitationUnit()))
                .queryParamIfPresent("timeformat", Optional.ofNullable(request.timeFormat()))
                .queryParamIfPresent("disable_bias_correction", Optional.ofNullable(request.disableBiasCorrection()))
                .queryParamIfPresent("cell_selection", Optional.ofNullable(request.cellSelection()))
                .build();

        return webClient.get()
                .uri(uriBuilderURIFunction)
                .retrieve()
                .bodyToMono(ClimateChangeRespose.class)
                .onErrorResume(error -> {
                    log.error("Error occurred while calling the OpenMeteo API for getting the climate statistics {}", error.getMessage());
                    return Mono.error(new OpenMeteoException("Error calling the OpenMeteo API for getting the climate statistics"));
                });
    }
}