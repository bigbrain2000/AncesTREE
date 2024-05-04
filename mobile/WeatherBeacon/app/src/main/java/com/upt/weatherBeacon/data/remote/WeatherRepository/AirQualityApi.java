package com.upt.weatherBeacon.data.remote.WeatherRepository;

import com.upt.weatherBeacon.data.remote.WeatherRepository.Dto.AirQuality;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AirQualityApi {
    @GET("air-quality")
    Call<AirQuality> getAirQuality(
            @Query("latitude") double latitude,
            @Query("longitude") double longitude,
            @Query("hourly") String hourly
    );
}
