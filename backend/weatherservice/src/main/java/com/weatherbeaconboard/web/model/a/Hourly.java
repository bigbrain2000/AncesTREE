package com.weatherbeaconboard.web.model.a;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public record Hourly(

	@SerializedName("sulphur_dioxide")
	List<Object> sulphurDioxide,

	@SerializedName("european_aqi_pm2_5")
	List<Integer> europeanAqiPm25,

	@SerializedName("birch_pollen")
	List<Object> birchPollen,

	@SerializedName("nitrogen_dioxide")
	List<Object> nitrogenDioxide,

	@SerializedName("uv_index_clear_sky")
	List<Object> uvIndexClearSky,

	@SerializedName("pm10")
	List<Object> pm10,

	@SerializedName("ammonia")
	List<Object> ammonia,

	@SerializedName("olive_pollen")
	List<Object> olivePollen,

	@SerializedName("ozone")
	List<Object> ozone,

	@SerializedName("european_aqi_ozone")
	List<Integer> europeanAqiOzone,

	@SerializedName("european_aqi_nitrogen_dioxide")
	List<Integer> europeanAqiNitrogenDioxide,

	@SerializedName("european_aqi_pm10")
	List<Integer> europeanAqiPm10,

	@SerializedName("uv_index")
	List<Object> uvIndex,

	@SerializedName("grass_pollen")
	List<Object> grassPollen,

	@SerializedName("pm2_5")
	List<Object> pm25,

	@SerializedName("aerosol_optical_depth")
	List<Object> aerosolOpticalDepth,

	@SerializedName("european_aqi_sulphur_dioxide")
	List<Integer> europeanAqiSulphurDioxide,

	@SerializedName("time")
	List<String> time,

	@SerializedName("ragweed_pollen")
	List<Object> ragweedPollen,

	@SerializedName("dust")
	List<Object> dust,

	@SerializedName("mugwort_pollen")
	List<Object> mugwortPollen,

	@SerializedName("carbon_monoxide")
	List<Object> carbonMonoxide,

	@SerializedName("alder_pollen")
	List<Object> alderPollen,

	@SerializedName("european_aqi")
	List<Integer> europeanAqi
) {
}