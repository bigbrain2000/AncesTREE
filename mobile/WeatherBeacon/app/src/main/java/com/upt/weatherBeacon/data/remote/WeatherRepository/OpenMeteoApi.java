package com.upt.weatherBeacon.data.remote.WeatherRepository;

import com.upt.weatherBeacon.data.remote.WeatherRepository.Dto.WeatherForecasts;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OpenMeteoApi {
//    @GET("forecast?latitude={latitude}&longitude={longitude}&current=temperature_2m&hourly=temperature_2m&forecast_days=1")
//    public Call<Object> getResposne(@Path("latitude") String latitude, @Path("longitude") String longitude);

    @GET("forecast")
    Call<WeatherForecasts> getWeatherData(
            @Query("latitude") String latitude,
            @Query("longitude") String longitude,
            @Query("current") String currentParams
    );

    @GET("forecast")
    Call<WeatherForecasts> getResponse(
            @Query("latitude") double latitude,
            @Query("longitude") double longitude,
            @Query("current") String currentParams,
            @Query("hourly") String hourlyParams,
            @Query("daily") String dailyParams
    );
    @GET("forecast")
    Call<WeatherForecasts> getResponse2(
            @Query("latitude") String latitude,
            @Query("longitude") String longitude,
            @Query("current") String current,
            @Query("hourly") String hourly,
            @Query("forecast_days") int forecastDays
    );

    @GET("forecast")
    Call<Object> getResponse1(
            @Query("latitude") String latitude,
            @Query("longitude") String longitude,
            @Query("current") String current,
            @Query("hourly") String hourly,
            @Query("forecast_days") int forecastDays
    );


//    https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&current=temperature_2m,relative_humidity_2m,weather_code,wind_speed_10m&hourly=temperature_2m,relative_humidity_2m,rain,weather_code,wind_speed_10m,wind_direction_10m&daily=weather_code,temperature_2m_max,temperature_2m_min,sunrise,sunset
//    GET("/forecast?latitude={latitude}&longitude={longitude}&h")
//    https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&hourly=temperature_2m,relative_humidity_2m,rain,weather_code,wind_speed_10m,wind_direction_10m
}
