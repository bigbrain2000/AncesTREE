package com.upt.weatherBeacon.AppState;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.upt.weatherBeacon.data.remote.WeatherRepository.Dto.AirQuality;
import com.upt.weatherBeacon.data.remote.WeatherRepository.Dto.Geocoding;
import com.upt.weatherBeacon.data.remote.WeatherRepository.Dto.HourlyAirQuality;
import com.upt.weatherBeacon.model.HourlyAirData;
import com.upt.weatherBeacon.model.User;
import com.upt.weatherBeacon.model.WeatherData;
import com.upt.weatherBeacon.model.YearGraphSeries;

import java.util.List;

public class GlobalState {
    private static GlobalState state=null;

    private String accessToken = "";
    private User usr;
    private String city = "";
    private Double latitude = 0.0;
    private Double longitude = 0.0;

    private MutableLiveData<WeatherData> weatherDataLiveData = new MutableLiveData<>();
    private MutableLiveData<Geocoding> geocodingLiveData = new MutableLiveData<>();

    private MutableLiveData<List<HourlyAirData>> airQualityLiveData = new MutableLiveData<>();

    public MutableLiveData<String> errorGeocoding = new MutableLiveData<>();

    public MutableLiveData<List<YearGraphSeries>> graphSeries = new MutableLiveData<>();

    public MutableLiveData<List<YearGraphSeries>> graphSeriesAll = new MutableLiveData<>();

    public MutableLiveData<String> jwtToken = new MutableLiveData<>();


    private GlobalState() {
    }

    public void setJwtToken(String token){ this.jwtToken.postValue(token); }
    public LiveData<String> getJwtToken(){ return this.jwtToken; }

    public static synchronized GlobalState getState() {
        if (state == null) {
            state = new GlobalState();
        }
        return state;

    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setUsr(User usr) {
        this.usr = usr;
    }

    public User getUsr() {
        return usr;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude){
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLongitude() {
        return longitude;
    }
    // Getter for hourly weather data LiveData
    public LiveData<WeatherData> getWeatherDataLiveData() {
        return weatherDataLiveData;
    }

    // Method to update hourly weather data LiveData
    public void updateWeatherData(WeatherData newData) {
        weatherDataLiveData.postValue(newData);
    }

    public LiveData<Geocoding> getGeocodingLiveData() {
        return this.geocodingLiveData;
    }

    public void updateGeocodingData(Geocoding newData) {
        geocodingLiveData.postValue(newData);
    }

    public LiveData<List<HourlyAirData>> getAirQualityLiveData() {
        return this.airQualityLiveData;
    }

    public void updateAirQualityLiveData(List<HourlyAirData> newData) {
        airQualityLiveData.postValue(newData);
    }

    public LiveData<List<YearGraphSeries>> getGraphSeries(){return this.graphSeries;}
    public void updateGraphSeriesLiveData(List<YearGraphSeries> series){
        graphSeries.postValue(series);
    }

    public LiveData<List<YearGraphSeries>> getGraphSeriesAll(){return this.graphSeriesAll;}
    public void updateGraphSeriesLiveDataAll(List<YearGraphSeries> series){
        graphSeriesAll.postValue(series);
    }

}
