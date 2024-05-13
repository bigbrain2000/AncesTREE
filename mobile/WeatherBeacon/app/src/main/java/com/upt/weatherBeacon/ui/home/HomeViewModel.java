package com.upt.weatherBeacon.ui.home;

import static com.upt.weatherBeacon.di.NetworkModule.provideAirQualityAPI;
import static com.upt.weatherBeacon.di.NetworkModule.provideGeocodingAPI;
import static com.upt.weatherBeacon.di.NetworkModule.provideOpenMeteoAPI;
import static com.upt.weatherBeacon.di.NetworkModule.provideUserApiService;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.upt.weatherBeacon.AppState.GlobalState;
import com.upt.weatherBeacon.data.remote.WeatherRepository.Dto.AirQuality;
import com.upt.weatherBeacon.data.remote.WeatherRepository.Dto.Geocoding;
import com.upt.weatherBeacon.data.remote.WeatherRepository.Dto.HourlyAirQuality;
import com.upt.weatherBeacon.data.remote.WeatherRepository.WeatherRepository;
import com.upt.weatherBeacon.data.remote.userRepository.UserRepository;
import com.upt.weatherBeacon.data.remote.userRepository.dtos.UserUpdateDto;
import com.upt.weatherBeacon.model.AirQaulityCallback;
import com.upt.weatherBeacon.model.GeocodingDataCallback;
import com.upt.weatherBeacon.model.GetUserCallback;
import com.upt.weatherBeacon.model.HourlyAirData;
import com.upt.weatherBeacon.model.User;
import com.upt.weatherBeacon.model.WeatherData;
import com.upt.weatherBeacon.model.WeatherDataCallback;
import com.upt.weatherBeacon.model.YearClimate;
import com.upt.weatherBeacon.model.YearGraphSeries;
import com.upt.weatherBeacon.ui.base.BaseViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class HomeViewModel extends BaseViewModel {

    private GlobalState appState = GlobalState.getState();
    @Inject
    public WeatherRepository repository;
    @Inject
    public UserRepository userRepository;

    public HomeViewModel(){
        this.repository = new WeatherRepository();
        this.repository.api = provideOpenMeteoAPI();
        this.repository.geoApi = provideGeocodingAPI();
        this.repository.airApi = provideAirQualityAPI();

        this.userRepository = new UserRepository();
        this.userRepository.userApi = provideUserApiService();
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

    public void getAirQualityForNewCity(double latitude, double longitude, String city){
        repository.getAirQualityData(latitude, longitude, new AirQaulityCallback() {
            @Override
            public void onAirDataReceived(List<HourlyAirData> data){
                appState.updateAirQualityLiveData(data);
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

    public void getUserLoggedInData(String username){
        userRepository.getUser(username, new GetUserCallback() {
            @Override
            public void onUserDataReceived(User user) {
                System.out.println("USER::: info account: "+ user.address);
                appState.setUser(user);
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<YearClimate> getClimateChangeData(){
        return repository.getCLimateChangeData();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<YearGraphSeries> getSeries(List<YearClimate> years){
        List<List<LineGraphSeries<DataPoint>>> series = new ArrayList<>();
        List<YearGraphSeries> yearGraphSeries = new ArrayList<>();
        for(int i =0; i < years.size(); i++){
            YearClimate year = years.get(i);
            DataPoint[] maxTempPoints = new DataPoint[year.days.length];
            DataPoint[] minTempPoints = new DataPoint[year.days.length];
            DataPoint[] windPoints = new DataPoint[year.days.length];
            DataPoint[] precipitationPoints = new DataPoint[year.days.length];
            for(int j = 0; j < year.days.length; j++){
                maxTempPoints[j] = new DataPoint(j, year.days[j].maxTemp);
                minTempPoints[j] = new DataPoint(j, year.days[j].minTemp);
                windPoints[j] = new DataPoint(j, year.days[j].windSpeed);
                precipitationPoints[j] = new DataPoint(j, year.days[j].precipitation);

            }
            List<LineGraphSeries<DataPoint>> yearGraphs = new ArrayList<>();

            yearGraphs.add( new LineGraphSeries(maxTempPoints));
            yearGraphs.add( new LineGraphSeries(minTempPoints));
            yearGraphs.add( new LineGraphSeries(windPoints));
            yearGraphs.add( new LineGraphSeries(precipitationPoints));

            YearGraphSeries yearS = new YearGraphSeries();
            yearS.series = yearGraphs;
            yearS.year = i+2019;
            System.out.println("GRAPH view model year "+ i + " "+ yearS.year + " "+ yearS.series.get(0));
            yearGraphSeries.add(yearS);
            series.add(yearGraphs);

        }



//        return series;
        return yearGraphSeries;
    }

    public void updateUser(String username, String firstName, String lastName, String email, String address, String password){

//        User user = new User(username,firstName, lastName, password, email, new Date(), "0727217100", address);
        UserUpdateDto user = new UserUpdateDto();
        user.address = address;
        user.email = email;
        user.phoneNumber = "0727217100";
        user.firstName = firstName;
        user.lastName = lastName;
        userRepository.updateUser(username, user);
    }

    public void deleteAccount(String username, String jwt){
        userRepository.deleteAccount(username, jwt);
    }

}
