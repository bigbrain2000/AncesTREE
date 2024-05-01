package com.weatherbeaconboard.web.model.forecast;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record ForecastWeatherResponse(

@JsonProperty("latitude") Object latitude,

@JsonProperty("longitude") Object longitude,

@JsonProperty("elevation") Object elevation,

@JsonProperty("generationtime_ms") Object generationtimeMs,

@JsonProperty("utc_offset_seconds") int utcOffsetSeconds,
@JsonProperty("timezone") String timezone,

@JsonProperty("timezone_abbreviation") String timezoneAbbreviation,

@JsonProperty("hourly") Hourly hourly,

@JsonProperty("hourly_units") HourlyUnits hourlyUnits,

@JsonProperty("daily") Daily daily,

@JsonProperty("daily_units") DailyUnits dailyUnits

        )implements Serializable{

        }