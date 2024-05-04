package com.upt.weatherBeacon.data.remote.WeatherRepository.Dto;

import com.google.gson.annotations.SerializedName;

public class AirQuality {
    @SerializedName("hourly")
    public HourlyAirQuality hourly;
}
