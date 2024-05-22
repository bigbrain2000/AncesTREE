package com.upt.weatherBeacon.data.remote.WeatherRepository.Dto;

import com.google.gson.annotations.SerializedName;

public class DailyClimateChange {
    @SerializedName("time")
    public String[] time;
    @SerializedName("temperature_2m_max")
    public double[] maxTemp;
    @SerializedName("temperature_2m_min")
    public double[] minTemp;
    @SerializedName("wind_speed_10m_max")
    public double[] maxWindSpeed;
    @SerializedName("precipitation_sum")
    public double[] precipitation;
}
