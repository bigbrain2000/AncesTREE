package com.weatherbeaconboard.web.model.airquality;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record AirQualityResponse(

@NotNull
@JsonProperty("latitude") Object latitude,

@NotNull
@JsonProperty("longitude") Object longitude,

@JsonProperty("elevation") Object elevation,
@JsonProperty("generationtime_ms") Object generationtimeMs,
@JsonProperty("utc_offset_seconds") int utcOffsetSeconds,
@JsonProperty("timezone") String timezone,
@JsonProperty("timezone_abbreviation") String timezoneAbbreviation,
@JsonProperty("hourly") Hourly hourly,
@JsonProperty("hourly_units") HourlyUnits hourlyUnits,
@JsonProperty("current") Current current,
@JsonProperty("current_units") CurrentUnits currentUnits

        )implements Serializable{

        }