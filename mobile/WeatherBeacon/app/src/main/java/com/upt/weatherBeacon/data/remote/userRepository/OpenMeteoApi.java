package com.upt.weatherBeacon.data.remote.userRepository;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OpenMeteoApi {
//    @GET("forecast?latitude={latitude}&longitude={longitude}&current=temperature_2m&hourly=temperature_2m&forecast_days=1")
//    public Call<Object> getResposne(@Path("latitude") String latitude, @Path("longitude") String longitude);

    @GET("forecast")
    Call<Object> getResponse(
            @Query("latitude") String latitude,
            @Query("longitude") String longitude,
            @Query("current") String current,
            @Query("hourly") String hourly,
            @Query("forecast_days") int forecastDays
    );
}
