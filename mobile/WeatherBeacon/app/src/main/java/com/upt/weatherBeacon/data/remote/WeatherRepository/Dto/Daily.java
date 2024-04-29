package com.upt.weatherBeacon.data.remote.WeatherRepository.Dto;

import com.google.gson.annotations.SerializedName;

public class Daily {

    @SerializedName("time")
    public String[] time;
    @SerializedName("weather_code")
    public int[] weather_code;
    @SerializedName("temperature_2m_max")
    public double[] temperature_max;
    @SerializedName("temperature_2m_min")
    public double[] temperature_2m_min;
    @SerializedName("sunrise")
    public String[] sunrise;
    @SerializedName("sunset")
    public String[] sunset;
}
