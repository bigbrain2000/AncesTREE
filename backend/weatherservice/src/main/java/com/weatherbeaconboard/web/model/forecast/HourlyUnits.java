package com.weatherbeaconboard.web.model.forecast;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record HourlyUnits(
		@JsonProperty("soil_moisture_0_to_1cm") String soilMoisture0To1cm,
		@JsonProperty("soil_moisture_1_to_3cm") String soilMoisture1To3cm,
		@JsonProperty("surface_pressure") String surfacePressure,
		@JsonProperty("soil_moisture_3_to_9cm") String soilMoisture3To9cm,
		@JsonProperty("relative_humidity_2m") String relativeHumidity2m,
		@JsonProperty("cloud_cover_low") String cloudCoverLow,
		@JsonProperty("snowfall") String snowfall,
		@JsonProperty("vapour_pressure_deficit") String vapourPressureDeficit,
		@JsonProperty("soil_temperature_0cm") String soilTemperature0cm,
		@JsonProperty("evapotranspiration") String evapotranspiration,
		@JsonProperty("soil_temperature_6cm") String soilTemperature6cm,
		@JsonProperty("soil_moisture_9_to_27cm") String soilMoisture9To27cm,
		@JsonProperty("shortwave_radiation") String shortwaveRadiation,
		@JsonProperty("precipitation") String precipitation,
		@JsonProperty("soil_temperature_54cm") String soilTemperature54cm,
		@JsonProperty("direct_radiation") String directRadiation,
		@JsonProperty("cloud_cover") String cloudCover,
		@JsonProperty("apparent_temperature") String apparentTemperature,
		@JsonProperty("wind_speed_80m") String windSpeed80m,
		@JsonProperty("cloud_cover_high") String cloudCoverHigh,
		@JsonProperty("snow_depth") String snowDepth,
		@JsonProperty("soil_temperature_18cm") String soilTemperature18cm,
		@JsonProperty("pressure_msl") String pressureMsl,
		@JsonProperty("wind_speed_10m") String windSpeed10m,
		@JsonProperty("global_tilted_irradiance") String globalTiltedIrradiance,
		@JsonProperty("rain") String rain,
		@JsonProperty("dew_point_2m") String dewPoint2m,
		@JsonProperty("visibility") String visibility,
		@JsonProperty("wind_speed_180m") String windSpeed180m,
		@JsonProperty("wind_direction_80m") String windDirection80m,
		@JsonProperty("is_day") String isDay,
		@JsonProperty("et0_fao_evapotranspiration") String et0FaoEvapotranspiration,
		@JsonProperty("soil_moisture_27_to_81cm") String soilMoisture27To81cm,
		@JsonProperty("wind_direction_10m") String windDirection10m,
		@JsonProperty("wind_gusts_10m") String windGusts10m,
		@JsonProperty("freezing_level_height") String freezingLevelHeight,
		@JsonProperty("temperature_2m") String temperature2m,
		@JsonProperty("cloud_cover_mid") String cloudCoverMid,
		@JsonProperty("showers") String showers,
		@JsonProperty("wind_direction_120m") String windDirection120m,
		@JsonProperty("precipitation_probability") String precipitationProbability,
		@JsonProperty("wind_direction_180m") String windDirection180m,
		@JsonProperty("time") String time,
		@JsonProperty("wind_speed_120m") String windSpeed120m,
		@JsonProperty("direct_normal_irradiance") String directNormalIrradiance,
		@JsonProperty("diffuse_radiation") String diffuseRadiation,
		@JsonProperty("cape") String cape,
		@JsonProperty("weather_code") String weatherCode

) implements Serializable {

}