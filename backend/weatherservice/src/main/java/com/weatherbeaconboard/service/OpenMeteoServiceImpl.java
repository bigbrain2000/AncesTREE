package com.weatherbeaconboard.service;

import com.weatherbeaconboard.client.flood.FloodStatisticsAsyncClient;
import com.weatherbeaconboard.client.forecast.ForecastWeatherAsyncClient;
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
public class OpenMeteoServiceImpl implements OpenMeteoService{

    private ForecastWeatherAsyncClient forecastWeatherAsyncClient;
    private FloodStatisticsAsyncClient floodStatisticsAsyncClient;

    public Mono<ForecastWeatherResponse> getForecastWeather(@NotNull @Valid ForecastWeatherRequest forecastWeatherRequest) {
       return  forecastWeatherAsyncClient.getForecastWeather(forecastWeatherRequest);
    }

    public Mono<FloodResponse> getFloodStatistics(@NotNull @Valid FloodRequest floodRequest) {
       return  floodStatisticsAsyncClient.getFloodStatistics(floodRequest);
    }
}
