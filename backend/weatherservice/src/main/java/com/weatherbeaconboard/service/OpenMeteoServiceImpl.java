package com.weatherbeaconboard.service;

import com.weatherbeaconboard.client.airquality.AirQualityStatisticsAsyncClient;
import com.weatherbeaconboard.client.climate.ClimateStatisticsAsyncClient;
import com.weatherbeaconboard.client.elevation.ElevationStatisticsAsyncClient;
import com.weatherbeaconboard.client.flood.FloodStatisticsAsyncClient;
import com.weatherbeaconboard.client.forecast.ForecastWeatherAsyncClient;
import com.weatherbeaconboard.web.model.airquality.AirQualityRequest;
import com.weatherbeaconboard.web.model.airquality.AirQualityResponse;
import com.weatherbeaconboard.web.model.climatechange.ClimateChangeRequest;
import com.weatherbeaconboard.web.model.climatechange.ClimateChangeRespose;
import com.weatherbeaconboard.web.model.elevation.ElevationRequest;
import com.weatherbeaconboard.web.model.elevation.ElevationResponse;
import com.weatherbeaconboard.web.model.flood.FloodRequest;
import com.weatherbeaconboard.web.model.flood.FloodResponse;
import com.weatherbeaconboard.web.model.forecast.ForecastWeatherRequest;
import com.weatherbeaconboard.web.model.forecast.ForecastWeatherResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@Validated
@AllArgsConstructor
public class OpenMeteoServiceImpl implements OpenMeteoService {

    private ForecastWeatherAsyncClient forecastWeatherAsyncClient;
    private FloodStatisticsAsyncClient floodStatisticsAsyncClient;
    private ElevationStatisticsAsyncClient elevationStatisticsAsyncClient;
    private AirQualityStatisticsAsyncClient airQualityStatisticsAsyncClient;
    private ClimateStatisticsAsyncClient climateStatisticsAsyncClient;

    public Mono<ForecastWeatherResponse> getForecastWeather(@NotNull @Valid ForecastWeatherRequest forecastWeatherRequest) {
        return forecastWeatherAsyncClient.getForecastWeather(forecastWeatherRequest);
    }

    public Mono<FloodResponse> getFloodStatistics(@NotNull @Valid FloodRequest floodRequest) {
        return floodStatisticsAsyncClient.getFloodStatistics(floodRequest);
    }

    public Mono<ElevationResponse> getElevationStatistics(@NotNull @Valid ElevationRequest elevationRequest) {
        return elevationStatisticsAsyncClient.getElevationStatistics(elevationRequest);
    }

    public Mono<AirQualityResponse> getAirQualityStatistics(@NotNull @Valid AirQualityRequest airQualityRequest) {
        return airQualityStatisticsAsyncClient.getAirQualityStatistics(airQualityRequest);
    }

    public Mono<ClimateChangeRespose> getClimateStatistics(@NotNull @Valid ClimateChangeRequest climateChangeRequest) {
        return climateStatisticsAsyncClient.getClimateStatistics(climateChangeRequest);
    }
}
