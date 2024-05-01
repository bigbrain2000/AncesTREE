package com.weatherbeaconboard.web.model.airquality;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record HourlyUnits(

@JsonProperty("sulphur_dioxide")
	String sulphurDioxide,

@JsonProperty("european_aqi_pm2_5")
	String europeanAqiPm25,

@JsonProperty("birch_pollen")
	String birchPollen,

@JsonProperty("nitrogen_dioxide")
	String nitrogenDioxide,

@JsonProperty("uv_index_clear_sky")
	String uvIndexClearSky,

@JsonProperty("pm10")
	String pm10,

@JsonProperty("ammonia")
	String ammonia,

@JsonProperty("olive_pollen")
	String olivePollen,

@JsonProperty("ozone")
	String ozone,

@JsonProperty("european_aqi_ozone")
	String europeanAqiOzone,

@JsonProperty("european_aqi_nitrogen_dioxide")
	String europeanAqiNitrogenDioxide,

@JsonProperty("european_aqi_pm10")
	String europeanAqiPm10,

@JsonProperty("uv_index")
	String uvIndex,

@JsonProperty("grass_pollen")
	String grassPollen,

@JsonProperty("pm2_5")
	String pm25,

@JsonProperty("aerosol_optical_depth")
	String aerosolOpticalDepth,

@JsonProperty("european_aqi_sulphur_dioxide")
	String europeanAqiSulphurDioxide,

@JsonProperty("time")
	String time,

@JsonProperty("ragweed_pollen")
	String ragweedPollen,

@JsonProperty("dust")
	String dust,

@JsonProperty("mugwort_pollen")
	String mugwortPollen,

@JsonProperty("carbon_monoxide")
	String carbonMonoxide,

@JsonProperty("alder_pollen")
	String alderPollen,

@JsonProperty("european_aqi")
	String europeanAqi

            )implements Serializable{

            }