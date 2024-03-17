package com.weatherbeaconboard.web.model.a;

import com.google.gson.annotations.SerializedName;

public record Current(

	@SerializedName("sulphur_dioxide")
	Object sulphurDioxide,

	@SerializedName("european_aqi_pm2_5")
	int europeanAqiPm25,

	@SerializedName("birch_pollen")
	Object birchPollen,

	@SerializedName("nitrogen_dioxide")
	Object nitrogenDioxide,

	@SerializedName("uv_index_clear_sky")
	Object uvIndexClearSky,

	@SerializedName("ammonia")
	Object ammonia,

	@SerializedName("ozone")
	Object ozone,

	@SerializedName("european_aqi_nitrogen_dioxide")
	int europeanAqiNitrogenDioxide,

	@SerializedName("uv_index")
	Object uvIndex,

	@SerializedName("pm2_5")
	Object pm25,

	@SerializedName("aerosol_optical_depth")
	Object aerosolOpticalDepth,

	@SerializedName("ragweed_pollen")
	Object ragweedPollen,

	@SerializedName("dust")
	Object dust,

	@SerializedName("mugwort_pollen")
	Object mugwortPollen,

	@SerializedName("alder_pollen")
	Object alderPollen,

	@SerializedName("european_aqi")
	int europeanAqi,

	@SerializedName("pm10")
	Object pm10,

	@SerializedName("olive_pollen")
	Object olivePollen,

	@SerializedName("european_aqi_ozone")
	int europeanAqiOzone,

	@SerializedName("european_aqi_pm10")
	int europeanAqiPm10,

	@SerializedName("grass_pollen")
	Object grassPollen,

	@SerializedName("european_aqi_sulphur_dioxide")
	int europeanAqiSulphurDioxide,

	@SerializedName("interval")
	int interval,

	@SerializedName("time")
	String time,

	@SerializedName("carbon_monoxide")
	Object carbonMonoxide
) {
}