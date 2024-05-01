package com.upt.weatherBeacon.data.remote.WeatherRepository;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.upt.weatherBeacon.R;
import com.upt.weatherBeacon.data.remote.WeatherRepository.Dto.WeatherForecasts;
import com.upt.weatherBeacon.model.CurrentWeather;
import com.upt.weatherBeacon.model.DailyWeatherData;
import com.upt.weatherBeacon.model.HourlyWeatherData;
import com.upt.weatherBeacon.model.WeatherData;
import com.upt.weatherBeacon.model.WeatherDataCallback;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepository {
    @Inject
    public OpenMeteoApi api;

    public void getData(Double latitude, Double longitude, WeatherDataCallback callback) {


        Call<WeatherForecasts> callAsync1 = api.getResponse(
                52.52, // latitude
                13.41, // longitude
                "temperature_2m,relative_humidity_2m,weather_code,wind_speed_10m", // current
                "temperature_2m,relative_humidity_2m,rain,weather_code,wind_speed_10m,wind_direction_10m", // hourly
                "weather_code,temperature_2m_max,temperature_2m_min,sunrise,sunset" // daily
        );
        callAsync1.enqueue(new Callback<WeatherForecasts>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<WeatherForecasts> call, Response<WeatherForecasts> response) {
                if (response.isSuccessful()) {
                    WeatherForecasts resp = response.body();
                    List<HourlyWeatherData> hourly = mapHourly(resp);
                    List<DailyWeatherData> daily = mapDaily(resp);
                    CurrentWeather current = new CurrentWeather();
                    LocalTime currentTime = null;
                    currentTime = LocalTime.now();
                    // Extract the hour from the current time
                    int currentHour = currentTime.getHour();

                    System.out.println("Response: "+ hourly.get(currentHour).weatherCode);
                    current.humidity = hourly.get(currentHour).humidity;
                    current.rain = hourly.get(currentHour).rain;
                    current.temperature = hourly.get(currentHour).temperature;
                    current.time = hourly.get(currentHour).time;
                    current.wind_speed = hourly.get(currentHour).windSpeed;
                    current.weatherDescription = hourly.get(currentHour).weatherDescription;


                    WeatherData data = new WeatherData();

                    data.current = current;
                    data.hourly = hourly;
                    data.daily = daily;
                    data.elevation = Double.parseDouble(resp.elevation);


//                list de weatherData
                    callback.onWeatherDataReceived(data);
                } else {
                    callback.onFailure(new Exception("Failed to fetch data")); // Handle failure
                }
            }
            @Override
            public void onFailure(Call<WeatherForecasts> call, Throwable throwable) {
                System.out.println(throwable);
            }
        });

    }

    private List<HourlyWeatherData> mapHourly(WeatherForecasts response) {
        List<HourlyWeatherData> hourly = new ArrayList<HourlyWeatherData>();
        for (String r : response.hourly.time) {
            String[] time = r.split("T");
            HourlyWeatherData it = new HourlyWeatherData();
            it.date = time[0];
            it.time = time[1];
            hourly.add(it);
        }
        for (int i = 0; i < response.hourly.temperature.length; i++) {
            hourly.get(i).temperature = response.hourly.temperature[i];
            hourly.get(i).humidity = response.hourly.relative_humidity[i];
            hourly.get(i).weatherCode = calculateWeatherCode(response.hourly.weather_code[i]);
            hourly.get(i).rain = response.hourly.rain[i];
            hourly.get(i).windSpeed = response.hourly.wind_speed[i];
            hourly.get(i).windDirection = response.hourly.wind_direction[i];
            hourly.get(i).weatherDescription = calculateDescription(response.hourly.weather_code[i]);
        }
        return hourly;
    }

    private List<DailyWeatherData> mapDaily(WeatherForecasts response) {
        List<DailyWeatherData> daily = new ArrayList<>();

        for (int i = 0; i < response.daily.time.size(); i++) {
            DailyWeatherData day = new DailyWeatherData();
            String[] time = response.daily.time.get(i).split("T");
            day.date = time[0];
            day.time = time[0];
            day.weatherCode = calculateWeatherCode(response.daily.weather_code[i]);
            day.max_temperature = response.daily.temperature_max[i];
            day.min_temperature = response.daily.temperature_min[i];
            day.sunrise = response.daily.sunrise[i];
            day.sunset = response.daily.sunset[i];
            day.weatherDescription = calculateDescription(response.daily.weather_code[i]);
        }

        return daily;

    }

    private int calculateWeatherCode(int code) {
        if (code == 0) return R.drawable.sunny_weather;
        if (code == 1) return R.drawable.mostlyclearday;
        if (code == 2) return R.drawable.cloudy;
        if (code == 3) return R.drawable.overcast;
        if (code == 61) return R.drawable.rainy;
        if (code == 62 || code == 63) return R.drawable.thunderstorm;
        return R.drawable.rainy;
    }

    private String calculateDescription(int code) {
        if (code == 0) return "Sunny weather expected";
        if (code == 1) return "Mostly clear";
        if (code == 2) return "Cloudy";
        if (code == 3) return "Overcast";
        if (code == 61) return "Rainy";
        if (code == 62 || code == 63) return "Thunder-Storm";
        return "Rainy";
    }

}
