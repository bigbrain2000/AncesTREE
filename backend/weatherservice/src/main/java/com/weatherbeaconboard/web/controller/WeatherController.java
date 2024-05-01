package com.weatherbeaconboard.web.controller;

import com.weatherbeaconboard.service.OpenMeteoServiceImpl;
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
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/v1", produces = APPLICATION_JSON_VALUE)
@Validated
public class WeatherController {

    private final OpenMeteoServiceImpl openMeteoService;

    @GetMapping("/forecast")
    public Mono<ResponseEntity<ForecastWeatherResponse>> getForecastWeather(@Valid @ModelAttribute ForecastWeatherRequest forecastWeatherRequest) {
        return openMeteoService.getForecastWeather(forecastWeatherRequest)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/flood")
    public Mono<ResponseEntity<FloodResponse>> getFloodStatistics(
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude,
            @RequestParam(value = "daily") String[] dailyVariables,
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
    public Mono<ResponseEntity<ElevationResponse>> getElevationStatistics(@Valid @ModelAttribute ElevationRequest elevationRequest) {
        return openMeteoService.getElevationStatistics(elevationRequest)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/air-quality")
    public Mono<ResponseEntity<AirQualityResponse>> getAirQualityStatistics(
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude,
            @RequestParam(value = "hourly", required = false) String[] hourly,
            @RequestParam(value = "current", required = false) String[] current,
            @RequestParam(value = "domains", defaultValue = "auto") String domains,
            @RequestParam(value = "timeformat", defaultValue = "iso8601") String timeformat,
            @RequestParam(value = "timezone", defaultValue = "GMT") String timezone,
            @RequestParam(value = "past_days", defaultValue = "0") Integer pastDays,
            @RequestParam(value = "forecast_days", defaultValue = "5") Integer forecastDays,
            @RequestParam(value = "start_date", required = false) String startDate,
            @RequestParam(value = "end_date", required = false) String endDate,
            @RequestParam(value = "start_hour", required = false) String startHour,
            @RequestParam(value = "end_hour", required = false) String endHour,
            @RequestParam(value = "cell_selection", defaultValue = "nearest") String cellSelection) {

        final AirQualityRequest airQualityRequest = AirQualityRequest.builder()
                .latitude(latitude)
                .longitude(longitude)
                .hourly(hourly)
                .current(current)
                .domains(domains)
                .timeformat(timeformat)
                .timezone(timezone)
                .pastDays(pastDays)
                .forecastDays(forecastDays)
                .startDate(startDate)
                .endDate(endDate)
                .startHour(startHour)
                .endHour(endHour)
                .cellSelection(cellSelection)
                .build();

        return openMeteoService.getAirQualityStatistics(airQualityRequest)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/climate")
    public Mono<ResponseEntity<ClimateChangeRespose>> getClimateStatistics(
            @RequestParam("latitude") List<Double> latitude,
            @RequestParam("longitude") List<Double> longitude,
            @RequestParam("start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam("models") List<String> models,
            @RequestParam("daily") List<String> daily,
            @RequestParam(value = "temperature_unit", required = false) String temperatureUnit,
            @RequestParam(value = "wind_speed_unit", required = false) String windSpeedUnit,
            @RequestParam(value = "precipitation_unit", required = false) String precipitationUnit,
            @RequestParam(value = "timeformat", required = false) String timeFormat,
            @RequestParam(value = "disable_bias_correction", required = false) Boolean disableBiasCorrection,
            @RequestParam(value = "cell_selection", required = false) String cellSelection) {

        final ClimateChangeRequest climateChangeRequest = ClimateChangeRequest.builder()
                .latitude(latitude)
                .longitude(longitude)
                .startDate(startDate)
                .endDate(endDate)
                .models(models)
                .daily(daily)
                .temperatureUnit(temperatureUnit)
                .windSpeedUnit(windSpeedUnit)
                .precipitationUnit(precipitationUnit)
                .timeFormat(timeFormat)
                .disableBiasCorrection(disableBiasCorrection)
                .cellSelection(cellSelection)
                .build();

        return openMeteoService.getClimateStatistics(climateChangeRequest)
                .map(ResponseEntity::ok);
    }
}
