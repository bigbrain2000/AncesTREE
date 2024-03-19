package com.weatherbeaconboard.web.model.forecast;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public record Daily(
        @JsonProperty("sunrise") List<String> sunrise,
        @JsonProperty("wind_speed_10m_max") List<Object> windSpeed10mMax,
        @JsonProperty("apparent_temperature_min") List<Object> apparentTemperatureMin,
        @JsonProperty("rain_sum") List<Object> rainSum,
        @JsonProperty("daylight_duration") List<Object> daylightDuration,
        @JsonProperty("snowfall_sum") List<Object> snowfallSum,
        @JsonProperty("uv_index_clear_sky_max") List<Object> uvIndexClearSkyMax,
        @JsonProperty("sunshine_duration") List<Object> sunshineDuration,
        @JsonProperty("apparent_temperature_max") List<Object> apparentTemperatureMax,
        @JsonProperty("temperature_2m_max") List<Object> temperature2mMax,
        @JsonProperty("wind_direction_10m_dominant") List<Integer> windDirection10mDominant,
        @JsonProperty("precipitation_probability_mean") List<Integer> precipitationProbabilityMean,
        @JsonProperty("showers_sum") List<Object> showersSum,
        @JsonProperty("precipitation_hours") List<Object> precipitationHours,
        @JsonProperty("shortwave_radiation_sum") List<Object> shortwaveRadiationSum,
        @JsonProperty("precipitation_probability_min") List<Integer> precipitationProbabilityMin,
        @JsonProperty("uv_index_max") List<Object> uvIndexMax,
        @JsonProperty("temperature_2m_min") List<Object> temperature2mMin,
        @JsonProperty("et0_fao_evapotranspiration") List<Object> et0FaoEvapotranspiration,
        @JsonProperty("wind_gusts_10m_max") List<Object> windGusts10mMax,
        @JsonProperty("precipitation_probability_max") List<Integer> precipitationProbabilityMax,
        @JsonProperty("sunset") List<String> sunset,
        @JsonProperty("time") List<String> time,
        @JsonProperty("weather_code") List<Integer> weatherCode,
        @JsonProperty("precipitation_sum") List<Object> precipitationSum

) implements Serializable {


}