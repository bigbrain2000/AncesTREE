package com.upt.weatherBeacon.ui.home;

import static com.upt.weatherBeacon.di.NetworkModule.provideGeocodingAPI;
import static com.upt.weatherBeacon.di.NetworkModule.provideOpenMeteoAPI;

import com.upt.weatherBeacon.AppState.GlobalState;
import com.upt.weatherBeacon.data.remote.WeatherRepository.Dto.Geocoding;
import com.upt.weatherBeacon.data.remote.WeatherRepository.WeatherRepository;
import com.upt.weatherBeacon.model.GeocodingDataCallback;
import com.upt.weatherBeacon.model.WeatherData;
import com.upt.weatherBeacon.model.WeatherDataCallback;
import com.upt.weatherBeacon.ui.base.BaseViewModel;

import javax.inject.Inject;

public class HomeViewModel extends BaseViewModel {

    private GlobalState appState = GlobalState.getState();
    @Inject
    public WeatherRepository repository;

    public HomeViewModel(){
        this.repository = new WeatherRepository();
        this.repository.api = provideOpenMeteoAPI();
        this.repository.geoApi = provideGeocodingAPI();
    }
    public void getGeocodingData(String cityName){
        if(repository!=null){
            repository.getGeoCodingData(cityName.trim(), new GeocodingDataCallback() {
                @Override
                public void onWeatherDataReceived(Geocoding data) {
                    appState.updateGeocodingData(data);
                    appState.errorGeocoding.postValue("");
                }

                @Override
                public void onFailure(Throwable throwable) {
                    appState.errorGeocoding.postValue("City not found!");
                }
            });
        }
    }

    public void getForecastsForNewCity(double latitude, double longitude, String city){
        repository.getData(latitude, longitude, new WeatherDataCallback() {
            @Override
            public void onWeatherDataReceived(WeatherData weatherData) {
                appState.setCity(city);
                appState.updateWeatherData(weatherData);
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

}
