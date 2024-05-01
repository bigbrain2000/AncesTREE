package com.weatherbeaconboard.web.model.forecast;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public record Hourly(
@JsonProperty("soil_moisture_0_to_1cm") List<Object> soilMoisture0To1cm,
@JsonProperty("soil_moisture_1_to_3cm") List<Object> soilMoisture1To3cm,
@JsonProperty("surface_pressure") List<Object> surfacePressure,
@JsonProperty("soil_moisture_3_to_9cm") List<Object> soilMoisture3To9cm,
@JsonProperty("relative_humidity_2m") List<Integer> relativeHumidity2m,
@JsonProperty("cloud_cover_low") List<Integer> cloudCoverLow,
@JsonProperty("snowfall") List<Object> snowfall,
@JsonProperty("vapour_pressure_deficit") List<Object> vapourPressureDeficit,
@JsonProperty("soil_temperature_0cm") List<Object> soilTemperature0cm,
@JsonProperty("evapotranspiration") List<Object> evapotranspiration,
@JsonProperty("soil_temperature_6cm") List<Object> soilTemperature6cm,
@JsonProperty("soil_moisture_9_to_27cm") List<Object> soilMoisture9To27cm,
@JsonProperty("shortwave_radiation") List<Object> shortwaveRadiation,
@JsonProperty("precipitation") List<Object> precipitation,
@JsonProperty("soil_temperature_54cm") List<Object> soilTemperature54cm,
@JsonProperty("direct_radiation") List<Object> directRadiation,
@JsonProperty("cloud_cover") List<Integer> cloudCover,
@JsonProperty("apparent_temperature") List<Object> apparentTemperature,
@JsonProperty("wind_speed_80m") List<Object> windSpeed80m,
@JsonProperty("cloud_cover_high") List<Integer> cloudCoverHigh,
@JsonProperty("snow_depth") List<Object> snowDepth,
@JsonProperty("soil_temperature_18cm") List<Object> soilTemperature18cm,
@JsonProperty("pressure_msl") List<Object> pressureMsl,
@JsonProperty("wind_speed_10m") List<Object> windSpeed10m,
@JsonProperty("global_tilted_irradiance") List<Object> globalTiltedIrradiance,
@JsonProperty("rain") List<Object> rain,
@JsonProperty("dew_point_2m") List<Object> dewPoint2m,
@JsonProperty("visibility") List<Object> visibility,
@JsonProperty("wind_speed_180m") List<Object> windSpeed180m,
@JsonProperty("wind_direction_80m") List<Integer> windDirection80m,
@JsonProperty("is_day") List<Integer> isDay,
@JsonProperty("et0_fao_evapotranspiration") List<Object> et0FaoEvapotranspiration,
@JsonProperty("soil_moisture_27_to_81cm") List<Object> soilMoisture27To81cm,
@JsonProperty("wind_direction_10m") List<Integer> windDirection10m,
@JsonProperty("wind_gusts10m") List<Object> windGusts10m,
@JsonProperty("freezing_level_height") List<Object> freezingLevelHeight,
@JsonProperty("temperature_2m") List<Object> temperature2m,
@JsonProperty("cloud_cover_mid") List<Integer> cloudCoverMid,
@JsonProperty("showers") List<Object> showers,
@JsonProperty("wind_direction_120m") List<Integer> windDirection120m,
@JsonProperty("precipitation_probability") List<Integer> precipitationProbability,
@JsonProperty("wind_direction_180m") List<Integer> windDirection180m,
@JsonProperty("time") List<String> time,
@JsonProperty("wind_speed_120m") List<Object> windSpeed120m,
@JsonProperty("direct_normal_irradiance") List<Object> directNormalIrradiance,
@JsonProperty("diffuse_radiation") List<Object> diffuseRadiation,
@JsonProperty("cape") List<Object> cape,
@JsonProperty("weather_code") List<Integer> weatherCode

        )implements Serializable{

        }
