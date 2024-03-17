package com.weatherbeaconboard.web.model.airquality;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

@Builder
public record AirQualityResponse(

        @JsonProperty("latitude") double latitude,
        @JsonProperty("longitude") double longitude,
        @JsonProperty("generationtime_ms") double generationTimeMs,
        @JsonProperty("utc_offset_seconds") int utcOffsetSeconds,
        @JsonProperty("timezone") String timezone,
        @JsonProperty("timezone_abbreviation") String timezoneAbbreviation,
        @JsonProperty("hourly") Objects hourlyData,
        @JsonProperty("hourly_units") Objects hourlyUnits

) implements Serializable {
}
