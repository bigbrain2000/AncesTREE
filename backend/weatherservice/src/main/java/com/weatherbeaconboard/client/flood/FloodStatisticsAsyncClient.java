package com.weatherbeaconboard.client.flood;

import com.weatherbeaconboard.config.FloodStatisticsClientProperties;
import com.weatherbeaconboard.exceptions.OpenMeteoException;
import com.weatherbeaconboard.web.model.flood.FloodRequest;
import com.weatherbeaconboard.web.model.flood.FloodResponse;
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
public class FloodStatisticsAsyncClient {

    private final WebClient webClient;
    private final FloodStatisticsClientProperties floodStatisticsClientProperties;

    public FloodStatisticsAsyncClient(@Qualifier("floodStatisticsWebClient") WebClient webClient,
                                      FloodStatisticsClientProperties floodStatisticsClientProperties) {

        this.webClient = webClient;
        this.floodStatisticsClientProperties = floodStatisticsClientProperties;
    }

    public Mono<FloodResponse> getFloodStatistics(@NotNull @Valid FloodRequest request) {
        log.debug("Calling OpenMeteo API to retrieve the flood statistics");

        String dailyParams;
        if (request.dailyVariables() != null && request.dailyVariables().length > 0) {
            dailyParams = String.join(",", request.dailyVariables());
        } else {
            dailyParams = null;
        }

        final Function<UriBuilder, URI> uriBuilderURIFunction = uriBuilder -> uriBuilder.path(floodStatisticsClientProperties.getOpenMeteoFloodStatisticsUrl())
                .queryParam("latitude", request.latitude())
                .queryParam("longitude", request.longitude())
                .queryParamIfPresent("daily", Optional.ofNullable(dailyParams))
                .queryParamIfPresent("timeformat", Optional.ofNullable(request.timeFormat()))
                .queryParamIfPresent("past_days", Optional.ofNullable(request.pastDays()))
                .queryParamIfPresent("forecast_days", Optional.ofNullable(request.forecastDays()))
                .queryParamIfPresent("start_date", Optional.ofNullable(request.startDate()))
                .queryParamIfPresent("end_date", Optional.ofNullable(request.endDate()))
                .queryParamIfPresent("ensemble", Optional.ofNullable(request.ensemble()))
                .queryParamIfPresent("cell_selection", Optional.ofNullable(request.cellSelection()))
                .build();

        return webClient.get()
                .uri(uriBuilderURIFunction)
                .retrieve()
                .bodyToMono(FloodResponse.class)
                .onErrorResume(error -> {
                    log.error("Error occurred while calling the OpenMeteo API for getting the flood statistics {}", error.getMessage());
                    return Mono.error(new OpenMeteoException("Error calling the OpenMeteo API for getting the flood statistics"));
                });
    }
}