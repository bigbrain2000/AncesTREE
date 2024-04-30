package com.upt.weatherBeacon.ui.splash;

import static com.upt.weatherBeacon.di.NetworkModule.provideOpenMeteoAPI;

import com.upt.weatherBeacon.AppState.GlobalState;
import com.upt.weatherBeacon.data.remote.WeatherRepository.WeatherRepository;
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
//            repository.getData(appState.getLatitude(), appState.getLongitude());
        } else {
            System.out.println("Repository is null!!!");
        }
    }
}
