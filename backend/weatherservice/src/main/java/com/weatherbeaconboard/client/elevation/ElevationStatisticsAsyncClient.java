package com.weatherbeaconboard.client.elevation;

import com.weatherbeaconboard.config.ElevationStatisticsClientProperties;
import com.weatherbeaconboard.config.FloodStatisticsClientProperties;
import com.weatherbeaconboard.exceptions.OpenMeteoException;
import com.weatherbeaconboard.web.model.elevation.ElevationRequest;
import com.weatherbeaconboard.web.model.elevation.ElevationResponse;
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
public class ElevationStatisticsAsyncClient {

    private final WebClient webClient;
    private final ElevationStatisticsClientProperties elevationStatisticsClientProperties;

    public ElevationStatisticsAsyncClient(@Qualifier("elevationStatisticsWebClient") WebClient webClient,
                                          ElevationStatisticsClientProperties elevationStatisticsClientProperties) {

        this.webClient = webClient;
        this.elevationStatisticsClientProperties = elevationStatisticsClientProperties;
    }

    public Mono<ElevationResponse> getElevationStatistics(@NotNull @Valid ElevationRequest request) {
        log.debug("Calling OpenMeteo API to retrieve the flood statistics");

        final Function<UriBuilder, URI> uriBuilderURIFunction = uriBuilder -> uriBuilder.path(elevationStatisticsClientProperties.getOpenMeteoElevationStatisticsUrl())
                .queryParam("latitude", request.latitude())
                .queryParam("longitude", request.longitude())
                .build();

        return webClient.get()
                .uri(uriBuilderURIFunction)
                .retrieve()
                .bodyToMono(ElevationResponse.class)
                .onErrorResume(error -> {
                    log.error("Error occurred while calling the OpenMeteo API for getting the elevation statistics {}", error.getMessage());
                    return Mono.error(new OpenMeteoException("Error calling the OpenMeteo API for getting the flood statistics"));
                });
    }
}