package com.weatherbeaconboard.service;

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

}
