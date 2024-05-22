package com.upt.weatherBeacon.ui.splash;

import static com.upt.weatherBeacon.di.NetworkModule.provideAirQualityAPI;
import static com.upt.weatherBeacon.di.NetworkModule.provideGeocodingAPI;
import static com.upt.weatherBeacon.di.NetworkModule.provideOpenMeteoAPI;
import static com.upt.weatherBeacon.di.NetworkModule.provideUserApiService;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.upt.weatherBeacon.AppState.GlobalState;
import com.upt.weatherBeacon.data.remote.WeatherRepository.Dto.Geocoding;
import com.upt.weatherBeacon.data.remote.WeatherRepository.WeatherRepository;
import com.upt.weatherBeacon.data.remote.userRepository.UserRepository;
import com.upt.weatherBeacon.model.AirQaulityCallback;
import com.upt.weatherBeacon.model.GeocodingDataCallback;
import com.upt.weatherBeacon.model.GetUserCallback;
import com.upt.weatherBeacon.model.HourlyAirData;
import com.upt.weatherBeacon.model.User;
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

    @Inject
    public UserRepository userRepository;


    public SplashViewModel(){
        this.repository = new WeatherRepository();
        this.repository.api = provideOpenMeteoAPI();
        this.repository.geoApi = provideGeocodingAPI();
        this.repository.airApi = provideAirQualityAPI();

        this.userRepository = new UserRepository();
        this.userRepository.userApi = provideUserApiService();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getWeatherDataForCurrentLocation() {
        if (repository != null) {
            System.out.println("NU E NULL REPOSITORY");
            repository.getCLimateChangeData();

            repository.getAirQualityData(appState.getLatitude(), appState.getLongitude(), new AirQaulityCallback() {
                @Override
                public void onAirDataReceived(List<HourlyAirData> data) {
                    System.out.println("AIR QAULITY APP STATE ::: "+data);
                    appState.updateAirQualityLiveData(data);
                }

                @Override
                public void onFailure(Throwable throwable) {

                }
            });

            repository.getGeoCodingData(appState.getCity(), new GeocodingDataCallback() {
                @Override
                public void onWeatherDataReceived(Geocoding data) {
                    System.out.println("Response GEOCODING DATA: "+ data.results.length);
                    appState.updateGeocodingData(data);
                }

                @Override
                public void onFailure(Throwable throwable) {
                System.out.println("GEOCODING ERROR!!!");
                }
            });
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
    public void getUserData(){
        userRepository.getUser("testLuky", new GetUserCallback() {
            @Override
            public void onUserDataReceived(User user) {

            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }
}
