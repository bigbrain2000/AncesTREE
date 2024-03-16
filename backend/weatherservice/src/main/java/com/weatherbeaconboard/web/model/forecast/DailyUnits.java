package com.weatherbeaconboard.web.model.forecast;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record DailyUnits(
		@JsonProperty("sunrise") String sunrise,
		@JsonProperty("wind_speed_10m_max") String windSpeed10mMax,
		@JsonProperty("apparent_temperature_min") String apparentTemperatureMin,
		@JsonProperty("rain_sum") String rainSum,
		@JsonProperty("daylight_duration") String daylightDuration,
		@JsonProperty("snowfall_sum") String snowfallSum,
		@JsonProperty("uv_index_clear_sky_max") String uvIndexClearSkyMax,
		@JsonProperty("sunshine_duration") String sunshineDuration,
		@JsonProperty("apparent_temperature_max") String apparentTemperatureMax,
		@JsonProperty("temperature_2m_max") String temperature2mMax,
		@JsonProperty("wind_direction_10m_dominant") String windDirection10mDominant,
		@JsonProperty("precipitation_probability_mean") String precipitationProbabilityMean,
		@JsonProperty("showers_sum") String showersSum,
		@JsonProperty("precipitation_hours") String precipitationHours,
		@JsonProperty("shortwave_radiation_sum") String shortwaveRadiationSum,
		@JsonProperty("precipitation_probability_min") String precipitationProbabilityMin,
		@JsonProperty("uv_index_max") String uvIndexMax,
		@JsonProperty("temperature_2m_min") String temperature2mMin,
		@JsonProperty("et0_fao_evapotranspiration") String et0FaoEvapotranspiration,
		@JsonProperty("wind_gusts_10m_max") String windGusts10mMax,
		@JsonProperty("precipitation_probability_max") String precipitationProbabilityMax,
		@JsonProperty("sunset") String sunset,
		@JsonProperty("time") String time,
		@JsonProperty("weather_code") String weatherCode,
		@JsonProperty("precipitation_sum") String precipitationSum

) implements Serializable {

}
