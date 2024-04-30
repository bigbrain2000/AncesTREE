package com.upt.weatherBeacon.ui.splash;

import static com.upt.weatherBeacon.di.NetworkModule.provideOpenMeteoAPI;

import com.upt.weatherBeacon.AppState.GlobalState;
import com.upt.weatherBeacon.data.remote.WeatherRepository.WeatherRepository;
import com.upt.weatherBeacon.model.WeatherData;
import com.upt.weatherBeacon.model.WeatherDataCallback;
import com.upt.weatherBeacon.ui.base.BaseViewModel;

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
                @Override
                public void onWeatherDataReceived(WeatherData weatherData) {
                    System.out.println("Response elevation :::"+ weatherData.elevation);

                    System.out.println("Response current temp :::"+ weatherData.current.temperature);

                    System.out.println("Response current weather code :::"+ weatherData.current.temperature);

                    System.out.println("Response current rain :::"+ weatherData.current.rain);
                    appState.updateWeatherData(weatherData);

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
