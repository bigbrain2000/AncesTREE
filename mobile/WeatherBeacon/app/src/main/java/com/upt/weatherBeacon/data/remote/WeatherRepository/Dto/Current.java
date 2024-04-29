package com.upt.weatherBeacon.data.remote.WeatherRepository.Dto;

import com.google.gson.annotations.SerializedName;

public class Current {
    @SerializedName("time")
    public String time;
    @SerializedName("temperature_2m")
    public double temperature;

    @SerializedName("relative_humidity_2m")
    public double relative_humidity;

    @SerializedName("weather_code")
    public int weather_code;

    @SerializedName("wind_speed_10m")
    public double wind_speed;
}
