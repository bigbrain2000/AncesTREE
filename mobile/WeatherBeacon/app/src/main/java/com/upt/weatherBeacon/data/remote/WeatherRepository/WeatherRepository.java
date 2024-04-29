package com.upt.weatherBeacon.data.remote.WeatherRepository;

import com.upt.weatherBeacon.data.remote.WeatherRepository.Dto.WeatherForecasts;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepository {
    @Inject
    public OpenMeteoApi api;

    public void getData(Double latitude, Double longitude) {

        Call<WeatherForecasts> callAsync1 = api.getResponse(
                52.52, // latitude
                13.41, // longitude
                "temperature_2m,relative_humidity_2m,weather_code,wind_speed_10m", // current
                "temperature_2m,relative_humidity_2m,rain,weather_code,wind_speed_10m,wind_direction_10m", // hourly
                "weather_code,temperature_2m_max,temperature_2m_min,sunrise,sunset" // daily
        );
        callAsync1.enqueue(new Callback<WeatherForecasts>() {
            @Override
            public void onResponse(Call<WeatherForecasts> call, Response<WeatherForecasts> response) {
                WeatherForecasts resp = response.body();
                System.out.println("Response: REPOSITORY:::: OBJECT ::" + resp.elevation);
                System.out.println("Response: REPOSITORY:::: OBJECT ::" + resp.current.time);
                System.out.println("Response: REPOSITORY:::: OBJECT ::" + resp.current.temperature);
            }

            @Override
            public void onFailure(Call<WeatherForecasts> call, Throwable throwable) {
                System.out.println(throwable);
            }
        });




    }

}
