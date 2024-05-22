package com.upt.weatherBeacon.data.remote.WeatherRepository.Dto;

import com.google.gson.annotations.SerializedName;

public class ClimateChange {
    @SerializedName("daily")
    public DailyClimateChange daily;
}
