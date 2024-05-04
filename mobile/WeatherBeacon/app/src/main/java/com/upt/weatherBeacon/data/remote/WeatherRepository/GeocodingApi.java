package com.upt.weatherBeacon.data.remote.WeatherRepository;

import com.upt.weatherBeacon.data.remote.WeatherRepository.Dto.Geocoding;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeocodingApi {
    @GET("search")
    Call<Geocoding> searchLocation(
            @Query("name") String name,
            @Query("count") int count,
            @Query("language") String language,
            @Query("format") String format
    );
}
