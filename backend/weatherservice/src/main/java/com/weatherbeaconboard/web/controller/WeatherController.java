package com.weatherbeaconboard.web.controller;

import com.weatherbeaconboard.service.OpenMeteoServiceImpl;
import com.weatherbeaconboard.web.model.elevation.ElevationRequest;
import com.weatherbeaconboard.web.model.elevation.ElevationResponse;
import com.weatherbeaconboard.web.model.flood.FloodRequest;
import com.weatherbeaconboard.web.model.flood.FloodResponse;
import com.weatherbeaconboard.web.model.forecast.ForecastWeatherRequest;
import com.weatherbeaconboard.web.model.forecast.ForecastWeatherResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/v1", produces = APPLICATION_JSON_VALUE)
@Validated
public class WeatherController {

    private final OpenMeteoServiceImpl openMeteoService;

    @GetMapping("/forecast")
    public Mono<ResponseEntity<ForecastWeatherResponse>> getForecastWeather(
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude,
            @RequestParam(value = "elevation", required = false) Double elevation,
            @RequestParam(value = "hourly", required = false) String[] hourly,
            @RequestParam(value = "daily", required = false) String[] daily,
            @RequestParam(value = "current", required = false) String[] current,
            @RequestParam(value = "temperature_unit", required = false) String temperatureUnit,
            @RequestParam(value = "wind_speed_unit", required = false) String windSpeedUnit,
            @RequestParam(value = "precipitation_unit", required = false) String precipitationUnit,
            @RequestParam(value = "timeformat", required = false) String timeformat,
            @RequestParam(value = "timezone", required = false) String timezone,
            @RequestParam(value = "past_days", required = false) Integer pastDays,
            @RequestParam(value = "forecast_days", required = false) Integer forecastDays,
            @RequestParam(value = "forecast_hours", required = false) Integer forecastHours,
            @RequestParam(value = "forecast_minutely_15", required = false) Integer forecastMinutely15,
            @RequestParam(value = "past_hours", required = false) Integer pastHours,
            @RequestParam(value = "past_minutely_15", required = false) Integer pastMinutely15,
            @RequestParam(value = "start_date", required = false) String startDate,
            @RequestParam(value = "end_date", required = false) String endDate,
            @RequestParam(value = "start_hour", required = false) Integer startHour,
            @RequestParam(value = "end_hour", required = false) Integer endHour,
            @RequestParam(value = "start_minutely_15", required = false) String startMinutely15,
            @RequestParam(value = "end_minutely_15", required = false) String endMinutely15,
            @RequestParam(value = "models", required = false) String[] models,
            @RequestParam(value = "cell_selection", required = false) String cellSelection) {

        final ForecastWeatherRequest forecastWeatherRequest = ForecastWeatherRequest.builder()
                .latitude(latitude)
                .longitude(longitude)
                .elevation(elevation)
                .hourly(hourly)
                .daily(daily)
                .current(current)
                .temperatureUnit(temperatureUnit)
                .windSpeedUnit(windSpeedUnit)
                .precipitationUnit(precipitationUnit)
                .timeFormat(timeformat)
                .timezone(timezone)
                .pastDays(pastDays)
                .forecastDays(forecastDays)
                .forecastHours(forecastHours)
                .forecastMinutely15(forecastMinutely15)
                .pastHours(pastHours)
                .pastMinutely15(pastMinutely15)
                .startDate(startDate)
                .endDate(endDate)
                .startHour(startHour)
                .endHour(endHour)
                .startMinutely15(startMinutely15)
                .endMinutely15(endMinutely15)
                .models(models)
                .cellSelection(cellSelection)
                .build();

        return openMeteoService.getForecastWeather(forecastWeatherRequest)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/flood")
    public Mono<ResponseEntity<FloodResponse>> getFloodStatistics(
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude,
            @RequestParam(value = "daily", required = false) String[] dailyVariables,
            @RequestParam(value = "timeformat", required = false) String timeformat,
            @RequestParam(value = "past_days", required = false) Integer pastDays,
            @RequestParam(value = "forecast_days", required = false) Integer forecastDays,
            @RequestParam(value = "start_date", required = false) String startDate,
            @RequestParam(value = "end_date", required = false) String endDate,
            @RequestParam(value = "ensemble", required = false) Boolean ensemble,
            @RequestParam(value = "cell_selection", required = false) String cellSelection) {

        final FloodRequest floodRequest = FloodRequest.builder()
                .latitude(latitude)
                .longitude(longitude)
                .dailyVariables(dailyVariables)
                .timeFormat(timeformat)
                .pastDays(pastDays)
                .forecastDays(forecastDays)
                .startDate(startDate)
                .endDate(endDate)
                .ensemble(ensemble)
                .cellSelection(cellSelection)
                .build();

        return openMeteoService.getFloodStatistics(floodRequest)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/elevation")
    public Mono<ResponseEntity<ElevationResponse>> getElevationStatistics(
            @RequestParam("latitude") String[] latitude,
            @RequestParam("longitude") String[] longitude) {

        final List<Double> latitudeList = Arrays.stream(latitude)
                .map(Double::parseDouble)
                .toList();

        final List<Double> longitudeList = Arrays.stream(longitude)
                .map(Double::parseDouble)
                .toList();

        final ElevationRequest elevationRequest = ElevationRequest.builder()
                .latitude(latitudeList)
                .longitude(longitudeList)
                .build();

        return openMeteoService.getElevationStatistics(elevationRequest)
                .map(ResponseEntity::ok);
    }
}
