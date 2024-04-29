package com.upt.weatherBeacon.data.remote.WeatherRepository.Dto;

import com.google.gson.annotations.SerializedName;

public class WeatherForecasts {
    @SerializedName("elevation")
    public String elevation;
    @SerializedName("current")
    public Current current;
//    @SerializedName("hourly")
//    public Hourly[] hourly;
//    @SerializedName("daily")
//    public Daily[] daily;
}
