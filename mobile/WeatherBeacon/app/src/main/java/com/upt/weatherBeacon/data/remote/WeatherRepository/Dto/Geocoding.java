package com.upt.weatherBeacon.data.remote.WeatherRepository.Dto;

import com.google.gson.annotations.SerializedName;

public class Geocoding {
    @SerializedName("results")
   public GeocodingData[] results;
}
