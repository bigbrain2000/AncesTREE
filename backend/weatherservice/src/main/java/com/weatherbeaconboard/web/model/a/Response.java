package com.weatherbeaconboard.web.model.a;

import com.google.gson.annotations.SerializedName;

public record Response(

	@SerializedName("elevation")
	Object elevation,

	@SerializedName("hourly_units")
	HourlyUnits hourlyUnits,

	@SerializedName("generationtime_ms")
	Object generationtimeMs,

	@SerializedName("current")
	Current current,

	@SerializedName("timezone_abbreviation")
	String timezoneAbbreviation,

	@SerializedName("current_units")
	CurrentUnits currentUnits,

	@SerializedName("timezone")
	String timezone,

	@SerializedName("latitude")
	Object latitude,

	@SerializedName("utc_offset_seconds")
	int utcOffsetSeconds,

	@SerializedName("hourly")
	Hourly hourly,

	@SerializedName("longitude")
	Object longitude
) {
}