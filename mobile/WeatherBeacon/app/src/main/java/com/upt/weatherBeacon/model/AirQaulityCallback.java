package com.upt.weatherBeacon.model;


import java.util.List;

public interface AirQaulityCallback {
    void onAirDataReceived(List<HourlyAirData> data);
    void onFailure(Throwable throwable);
}
