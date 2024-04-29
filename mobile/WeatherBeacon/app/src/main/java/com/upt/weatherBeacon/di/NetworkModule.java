package com.upt.weatherBeacon.di;

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
        return new Retrofit.Builder()
                .baseUrl(Config.USER)
                .addConverterFactory(GsonConverterFactory.create())
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
    public static UserAPI provideUserApiService(Retrofit retrofit) {
        return retrofit.create(UserAPI.class);
    }


}