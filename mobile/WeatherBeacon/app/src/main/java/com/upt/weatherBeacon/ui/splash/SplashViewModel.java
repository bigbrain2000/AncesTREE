package com.upt.weatherBeacon.ui.splash;

import static com.upt.weatherBeacon.di.NetworkModule.provideOpenMeteoAPI;

import android.annotation.SuppressLint;

import com.upt.weatherBeacon.AppState.GlobalState;
import com.upt.weatherBeacon.data.remote.WeatherRepository.WeatherRepository;
import com.upt.weatherBeacon.model.WeatherData;
import com.upt.weatherBeacon.model.WeatherDataCallback;
import com.upt.weatherBeacon.ui.base.BaseViewModel;

import java.time.LocalTime;
import java.util.List;

import javax.inject.Inject;


public class SplashViewModel extends BaseViewModel {
    private GlobalState appState = GlobalState.getState();
    @Inject
    public WeatherRepository repository;


    public SplashViewModel(){
        this.repository = new WeatherRepository();
        this.repository.api = provideOpenMeteoAPI();
    }

    public void getWeatherDataForCurrentLocation() {
        if (repository != null) {
            System.out.println("NU E NULL REPOSITORY");
            repository.getData(appState.getLatitude(), appState.getLongitude(), new WeatherDataCallback() {
                @SuppressLint("NewApi")
                @Override
                public void onWeatherDataReceived(WeatherData weatherData) {
                    System.out.println("Response elevation :::"+ weatherData.elevation);

                    System.out.println("Response current temp :::"+ weatherData.current.temperature);

                    System.out.println("Response current weather code :::"+ weatherData.current.temperature);

                    System.out.println("Response current rain :::"+ weatherData.current.rain);
                    LocalTime currentTime = null;
                    currentTime = LocalTime.now();
                    // Extract the hour from the current time
                    int currentHour = currentTime.getHour();
                    WeatherData hours24 = weatherData;
                    hours24.hourly = weatherData.hourly.subList(0, currentHour+24);
                    appState.updateWeatherData(hours24);
                }

                @Override
                public void onFailure(Throwable throwable) {

                }
            });

        } else {
            System.out.println("Repository is null!!!");
        }
    }
}
