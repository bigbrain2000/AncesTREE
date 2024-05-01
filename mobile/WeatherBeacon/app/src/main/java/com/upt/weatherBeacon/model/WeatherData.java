package com.upt.weatherBeacon.model;

import java.util.List;

public class WeatherData {
    public Double elevation;

    public CurrentWeather current;
    public List<DailyWeatherData> daily;
    public List<HourlyWeatherData> hourly;

}
