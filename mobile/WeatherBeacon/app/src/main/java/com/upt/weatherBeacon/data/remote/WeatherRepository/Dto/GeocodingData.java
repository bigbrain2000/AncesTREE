package com.upt.weatherBeacon.data.remote.WeatherRepository.Dto;

import com.google.gson.annotations.SerializedName;

public class GeocodingData {
    @SerializedName("name")
    public String name;
    @SerializedName("latitude")
    public double latitude;
    @SerializedName("longitude")
    public double longitude;
    @SerializedName("elevation")
    public double elevation;
    @SerializedName("timezone")
    public String timezone;
    @SerializedName("country")
    public String country;
    @SerializedName("population")
    public double population;
    @SerializedName("admin1")
    public String admin1;

}
