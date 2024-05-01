package com.weatherbeaconboard.web.model.airquality;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record CurrentUnits(

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

@JsonProperty("ammonia")
	String ammonia,

@JsonProperty("ozone")
	String ozone,

@JsonProperty("european_aqi_nitrogen_dioxide")
	String europeanAqiNitrogenDioxide,

@JsonProperty("uv_index")
	String uvIndex,

@JsonProperty("pm2_5")
	String pm25,

@JsonProperty("aerosol_optical_depth")
	String aerosolOpticalDepth,

@JsonProperty("ragweed_pollen")
	String ragweedPollen,

@JsonProperty("dust")
	String dust,

@JsonProperty("mugwort_pollen")
	String mugwortPollen,

@JsonProperty("alder_pollen")
	String alderPollen,

@JsonProperty("european_aqi")
	String europeanAqi,

@JsonProperty("pm10")
	String pm10,

@JsonProperty("olive_pollen")
	String olivePollen,

@JsonProperty("european_aqi_ozone")
	String europeanAqiOzone,

@JsonProperty("european_aqi_pm10")
	String europeanAqiPm10,

@JsonProperty("grass_pollen")
	String grassPollen,

@JsonProperty("european_aqi_sulphur_dioxide")
	String europeanAqiSulphurDioxide,

@JsonProperty("interval")
	String interval,

@JsonProperty("time")
	String time,

@JsonProperty("carbon_monoxide")
	String carbonMonoxide

            )implements Serializable{

            }