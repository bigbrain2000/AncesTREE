package com.weatherbeaconboard.web.model.airquality;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record Current(

	@JsonProperty("sulphur_dioxide")
	Object sulphurDioxide,

	@JsonProperty("european_aqi_pm2_5")
	int europeanAqiPm25,

	@JsonProperty("birch_pollen")
	Object birchPollen,

	@JsonProperty("nitrogen_dioxide")
	Object nitrogenDioxide,

	@JsonProperty("uv_index_clear_sky")
	Object uvIndexClearSky,

	@JsonProperty("ammonia")
	Object ammonia,

	@JsonProperty("ozone")
	Object ozone,

	@JsonProperty("european_aqi_nitrogen_dioxide")
	int europeanAqiNitrogenDioxide,

	@JsonProperty("uv_index")
	Object uvIndex,

	@JsonProperty("pm2_5")
	Object pm25,

	@JsonProperty("aerosol_optical_depth")
	Object aerosolOpticalDepth,

	@JsonProperty("ragweed_pollen")
	Object ragweedPollen,

	@JsonProperty("dust")
	Object dust,

	@JsonProperty("mugwort_pollen")
	Object mugwortPollen,

	@JsonProperty("alder_pollen")
	Object alderPollen,

	@JsonProperty("european_aqi")
	int europeanAqi,

	@JsonProperty("pm10")
	Object pm10,

	@JsonProperty("olive_pollen")
	Object olivePollen,

	@JsonProperty("european_aqi_ozone")
	int europeanAqiOzone,

	@JsonProperty("european_aqi_pm10")
	int europeanAqiPm10,

	@JsonProperty("grass_pollen")
	Object grassPollen,

	@JsonProperty("european_aqi_sulphur_dioxide")
	int europeanAqiSulphurDioxide,

	@JsonProperty("interval")
	int interval,

	@JsonProperty("time")
	String time,

	@JsonProperty("carbon_monoxide")
	Object carbonMonoxide

) implements Serializable {

}