package com.upt.weatherBeacon.model;

import com.upt.weatherBeacon.data.remote.WeatherRepository.Dto.Geocoding;

public interface GetUserCallback {
    void onUserDataReceived(User user);
    void onFailure(Throwable throwable);
}
