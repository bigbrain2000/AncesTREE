package com.upt.weatherBeacon.data.remote.WeatherRepository.Dto;

import com.google.gson.annotations.SerializedName;

public class HourlyAirQuality {
    @SerializedName("time")
    public String[] time;
    @SerializedName("pm10")
    public Double[] pm10;
    @SerializedName("pm2_5")
    public Double[] pm2_5;
    @SerializedName("carbon_monoxide")
    public Double[] carbon_monoxide;
    @SerializedName("nitrogen_dioxide")
    public Double[] nitrogen_dioxide;
    @SerializedName("sulphur_dioxide")
    public Double[] sulphur_dioxide;
    @SerializedName("dust")
    public Integer[] dust;
    @SerializedName("uv_index")
    public Double[] uv_index;

    public String[] airDescription ={};
    public int[] airCode={};

}
