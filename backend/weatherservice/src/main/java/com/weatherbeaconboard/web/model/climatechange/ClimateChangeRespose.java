package com.weatherbeaconboard.web.model.climatechange;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record ClimateChangeRespose(

@JsonProperty("latitude") Object latitude,
@JsonProperty("longitude") Object longitude,
@JsonProperty("generationtime_ms") Object generationtimeMs,
@JsonProperty("timezone") String timezone,
@JsonProperty("timezone_abbreviation") String timezoneAbbreviation,
@JsonProperty("utc_offset_seconds") int utcOffsetSeconds,
@JsonProperty("daily") Daily daily,
@JsonProperty("daily_units") DailyUnits dailyUnits

        )implements Serializable{

        }
