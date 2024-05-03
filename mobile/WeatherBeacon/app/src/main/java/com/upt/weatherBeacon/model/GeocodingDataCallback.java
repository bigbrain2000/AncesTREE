package com.upt.weatherBeacon.model;

import com.upt.weatherBeacon.data.remote.WeatherRepository.Dto.Geocoding;

public interface GeocodingDataCallback {
    void onWeatherDataReceived(Geocoding data);
    void onFailure(Throwable throwable);
}
