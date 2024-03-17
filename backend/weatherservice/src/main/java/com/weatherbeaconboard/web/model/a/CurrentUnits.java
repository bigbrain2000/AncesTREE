package com.weatherbeaconboard.web.model.a;

import com.google.gson.annotations.SerializedName;

public record CurrentUnits(

	@SerializedName("sulphur_dioxide")
	String sulphurDioxide,

	@SerializedName("european_aqi_pm2_5")
	String europeanAqiPm25,

	@SerializedName("birch_pollen")
	String birchPollen,

	@SerializedName("nitrogen_dioxide")
	String nitrogenDioxide,

	@SerializedName("uv_index_clear_sky")
	String uvIndexClearSky,

	@SerializedName("ammonia")
	String ammonia,

	@SerializedName("ozone")
	String ozone,

	@SerializedName("european_aqi_nitrogen_dioxide")
	String europeanAqiNitrogenDioxide,

	@SerializedName("uv_index")
	String uvIndex,

	@SerializedName("pm2_5")
	String pm25,

	@SerializedName("aerosol_optical_depth")
	String aerosolOpticalDepth,

	@SerializedName("ragweed_pollen")
	String ragweedPollen,

	@SerializedName("dust")
	String dust,

	@SerializedName("mugwort_pollen")
	String mugwortPollen,

	@SerializedName("alder_pollen")
	String alderPollen,

	@SerializedName("european_aqi")
	String europeanAqi,

	@SerializedName("pm10")
	String pm10,

	@SerializedName("olive_pollen")
	String olivePollen,

	@SerializedName("european_aqi_ozone")
	String europeanAqiOzone,

	@SerializedName("european_aqi_pm10")
	String europeanAqiPm10,

	@SerializedName("grass_pollen")
	String grassPollen,

	@SerializedName("european_aqi_sulphur_dioxide")
	String europeanAqiSulphurDioxide,

	@SerializedName("interval")
	String interval,

	@SerializedName("time")
	String time,

	@SerializedName("carbon_monoxide")
	String carbonMonoxide
) {
}