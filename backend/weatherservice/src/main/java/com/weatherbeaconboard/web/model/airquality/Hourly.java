package com.weatherbeaconboard.web.model.airquality;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;


public record Hourly(

	@JsonProperty("sulphur_dioxide")
	List<Object> sulphurDioxide,

	@JsonProperty("european_aqi_pm2_5")
	List<Integer> europeanAqiPm25,

	@JsonProperty("birch_pollen")
	List<Object> birchPollen,

	@JsonProperty("nitrogen_dioxide")
	List<Object> nitrogenDioxide,

	@JsonProperty("uv_index_clear_sky")
	List<Object> uvIndexClearSky,

	@JsonProperty("pm10")
	List<Object> pm10,

	@JsonProperty("ammonia")
	List<Object> ammonia,

	@JsonProperty("olive_pollen")
	List<Object> olivePollen,

	@JsonProperty("ozone")
	List<Object> ozone,

	@JsonProperty("european_aqi_ozone")
	List<Integer> europeanAqiOzone,

	@JsonProperty("european_aqi_nitrogen_dioxide")
	List<Integer> europeanAqiNitrogenDioxide,

	@JsonProperty("european_aqi_pm10")
	List<Integer> europeanAqiPm10,

	@JsonProperty("uv_index")
	List<Object> uvIndex,

	@JsonProperty("grass_pollen")
	List<Object> grassPollen,

	@JsonProperty("pm2_5")
	List<Object> pm25,

	@JsonProperty("aerosol_optical_depth")
	List<Object> aerosolOpticalDepth,

	@JsonProperty("european_aqi_sulphur_dioxide")
	List<Integer> europeanAqiSulphurDioxide,

	@JsonProperty("time")
	List<String> time,

	@JsonProperty("ragweed_pollen")
	List<Object> ragweedPollen,

	@JsonProperty("dust")
	List<Object> dust,

	@JsonProperty("mugwort_pollen")
	List<Object> mugwortPollen,

	@JsonProperty("carbon_monoxide")
	List<Object> carbonMonoxide,

	@JsonProperty("alder_pollen")
	List<Object> alderPollen,

	@JsonProperty("european_aqi")
	List<Integer> europeanAqi

) implements Serializable {

}