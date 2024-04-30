package com.upt.weatherBeacon.model;

import java.util.List;

public interface WeatherDataCallback {
    void onWeatherDataReceived(WeatherData weatherData);
    void onFailure(Throwable throwable);
}