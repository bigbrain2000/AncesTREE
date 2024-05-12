package com.upt.weatherBeacon.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.upt.weatherBeacon.data.remote.WeatherRepository.AirQualityApi;
import com.upt.weatherBeacon.data.remote.WeatherRepository.Dto.AirQuality;
import com.upt.weatherBeacon.data.remote.WeatherRepository.Dto.GeocodingData;
import com.upt.weatherBeacon.data.remote.WeatherRepository.GeocodingApi;
import com.upt.weatherBeacon.data.remote.WeatherRepository.OpenMeteoApi;
import com.upt.weatherBeacon.data.remote.userRepository.UserAPI;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {
    @Provides
    @Singleton
    @Named("authorization")
    public static Retrofit provideRetrofitAuthorization() {
        return new Retrofit.Builder()
                .baseUrl(Config.AUTHORIZATION)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().build())
                .build();
    }
    @Provides
    @Singleton
    @Named("user")
    public static Retrofit provideRetrofitUser() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return new Retrofit.Builder()
                .baseUrl(Config.USER)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(new OkHttpClient.Builder().build())
                .build();
    }
    @Provides
    @Singleton
    @Named("weather")
    public static Retrofit provideRetrofitWeather() {
        return new Retrofit.Builder()
                .baseUrl(Config.WEATHER)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().build())
                .build();
    }
    public static OpenMeteoApi provideOpenMeteoAPI(){
        Retrofit retrofit = provideRetrofitWeather();
        return retrofit.create(OpenMeteoApi.class);
    }

    @Provides
    @Singleton
    @Named("geocoding")
    public static Retrofit provideRetrofitGeocoding() {
        return new Retrofit.Builder()
                .baseUrl(Config.GEOCODING)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().build())
                .build();
    }
    public static GeocodingApi provideGeocodingAPI(){
        Retrofit retrofit = provideRetrofitGeocoding();
        return retrofit.create(GeocodingApi.class);
    }

    @Provides
    @Singleton
    @Named("airQuality")
    public static Retrofit provideRetrofitAirQuality() {
        return new Retrofit.Builder()
                .baseUrl(Config.AIRQUALITY)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().build())
                .build();
    }
    public static AirQualityApi provideAirQualityAPI(){
        Retrofit retrofit = provideRetrofitAirQuality();
        return retrofit.create(AirQualityApi.class);
    }


    @Provides
    @Singleton
    public static UserAPI provideUserApiService() {
        Retrofit retrofit = provideRetrofitUser();
        return retrofit.create(UserAPI.class);
    }



}