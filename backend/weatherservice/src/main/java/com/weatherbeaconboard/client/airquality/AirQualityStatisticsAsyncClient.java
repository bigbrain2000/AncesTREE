package com.weatherbeaconboard.client.airquality;

import com.weatherbeaconboard.config.AirQualityStatisticsClientProperties;
import com.weatherbeaconboard.exceptions.OpenMeteoException;
import com.weatherbeaconboard.web.model.airquality.AirQualityRequest;
import com.weatherbeaconboard.web.model.airquality.AirQualityResponse;
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
public class AirQualityStatisticsAsyncClient {

    private final WebClient webClient;
    private final AirQualityStatisticsClientProperties airQualityStatisticsClientProperties;

    public AirQualityStatisticsAsyncClient(@Qualifier("airQualityStatisticsWebClient") WebClient webClient,
                                           AirQualityStatisticsClientProperties airQualityStatisticsClientProperties) {

        this.webClient = webClient;
        this.airQualityStatisticsClientProperties = airQualityStatisticsClientProperties;
    }

    public Mono<AirQualityResponse> getAirQualityStatistics(@NotNull @Valid AirQualityRequest request) {
        log.debug("Calling OpenMeteo API to retrieve the air quality statistics");

        String hourlyParams;
        if (request.hourly() != null && request.hourly().length > 0) {
            hourlyParams = String.join(",", request.hourly());
        } else {
            hourlyParams = null;
        }

        String currentParams;
        if (request.current() != null && request.current().length > 0) {
            currentParams = String.join(",", request.current());
        } else {
            currentParams = null;
        }

        final Function<UriBuilder, URI> uriBuilderURIFunction = uriBuilder ->
                uriBuilder.path(airQualityStatisticsClientProperties.getOpenMeteoAirQualityStatisticsUrl())
                        .queryParam("latitude", request.latitude())
                        .queryParam("longitude", request.longitude())
                        .queryParamIfPresent("hourly", Optional.ofNullable(hourlyParams))
                        .queryParamIfPresent("current", Optional.ofNullable(currentParams))
                        .queryParamIfPresent("domains", Optional.ofNullable(request.domains()))
                        .queryParam("timeformat", request.timeformat())
                        .queryParam("timezone", request.timezone())
                        .queryParamIfPresent("past_days", Optional.ofNullable(request.pastDays()))
                        .queryParamIfPresent("forecast_days", Optional.ofNullable(request.forecastDays()))
                        .queryParamIfPresent("forecast_hours", Optional.ofNullable(request.forecastHours()))
                        .queryParamIfPresent("past_hours", Optional.ofNullable(request.pastHours()))
                        .queryParamIfPresent("start_date", Optional.ofNullable(request.startDate()))
                        .queryParamIfPresent("end_date", Optional.ofNullable(request.endDate()))
                        .queryParamIfPresent("start_hour", Optional.ofNullable(request.startHour()))
                        .queryParamIfPresent("end_hour", Optional.ofNullable(request.endHour()))
                        .queryParam("cell_selection", request.cellSelection())
                        .build();

        return webClient.get()
                .uri(uriBuilderURIFunction)
                .retrieve()
                .bodyToMono(AirQualityResponse.class)
                .onErrorResume(error -> {
                    log.error("Error occurred while calling the OpenMeteo API for getting the  air quality statistics {}", error.getMessage());
                    return Mono.error(new OpenMeteoException("Error calling the OpenMeteo API for getting the  air quality statistics"));
                });
    }
}