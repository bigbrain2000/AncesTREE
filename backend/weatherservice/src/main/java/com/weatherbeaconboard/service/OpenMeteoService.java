package com.weatherbeaconboard.service;

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
import reactor.core.publisher.Mono;

public interface OpenMeteoService {

    Mono<ForecastWeatherResponse> getForecastWeather(@NotNull @Valid ForecastWeatherRequest forecastWeatherRequest);

    Mono<FloodResponse> getFloodStatistics(@NotNull @Valid FloodRequest floodRequest);

    Mono<ElevationResponse> getElevationStatistics(@NotNull @Valid ElevationRequest elevationRequest);

    Mono<AirQualityResponse> getAirQualityStatistics(@NotNull @Valid AirQualityRequest airQualityRequest);

    Mono<ClimateChangeRespose> getClimateStatistics(@NotNull @Valid ClimateChangeRequest climateChangeRequest);
}
