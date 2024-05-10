package com.upt.weatherBeacon.data.remote.WeatherRepository;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.upt.weatherBeacon.R;
import com.upt.weatherBeacon.assets.ClimateChangeData;
import com.upt.weatherBeacon.data.remote.WeatherRepository.Dto.AirQuality;
import com.upt.weatherBeacon.data.remote.WeatherRepository.Dto.ClimateChange;
import com.upt.weatherBeacon.data.remote.WeatherRepository.Dto.Geocoding;
import com.upt.weatherBeacon.data.remote.WeatherRepository.Dto.HourlyAirQuality;
import com.upt.weatherBeacon.data.remote.WeatherRepository.Dto.WeatherForecasts;
import com.upt.weatherBeacon.model.AirQaulityCallback;
import com.upt.weatherBeacon.model.CurrentWeather;
import com.upt.weatherBeacon.model.DailyWeatherData;
import com.upt.weatherBeacon.model.DayClimate;
import com.upt.weatherBeacon.model.GeocodingDataCallback;
import com.upt.weatherBeacon.model.HourlyAirData;
import com.upt.weatherBeacon.model.HourlyWeatherData;
import com.upt.weatherBeacon.model.WeatherData;
import com.upt.weatherBeacon.model.WeatherDataCallback;
import com.upt.weatherBeacon.model.YearClimate;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepository {
    @Inject
    public OpenMeteoApi api;
    @Inject
    public GeocodingApi geoApi;

    @Inject
    public AirQualityApi airApi;

    public void getData(Double latitude, Double longitude, WeatherDataCallback callback) {

        System.out.println("GetData weather repository!");


        Call<WeatherForecasts> callAsync1 = api.getResponse(
                latitude,
                longitude,
//                "temperature_2m,relative_humidity_2m,weather_code,wind_speed_10m", // current
                "temperature_2m,relative_humidity_2m,rain,weather_code,wind_speed_10m,wind_direction_10m", // hourly
                "weather_code,temperature_2m_max,temperature_2m_min,sunrise,sunset" // daily
        );
        callAsync1.enqueue(new Callback<WeatherForecasts>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<WeatherForecasts> call, Response<WeatherForecasts> response) {
                System.out.println("GetData weather repository onResponse ::: "+response);
                if (response.isSuccessful()) {
                    WeatherForecasts resp = response.body();
                    List<HourlyWeatherData> hourly = mapHourly(resp);
                    List<DailyWeatherData> daily = mapDaily(resp);
                    CurrentWeather current = new CurrentWeather();
                    LocalTime currentTime = null;
                    currentTime = LocalTime.now();
                    // Extract the hour from the current time
                    int currentHour = currentTime.getHour();
                    System.out.println("Daily response elevation ::: " + response.body().elevation);

                    System.out.println("Daily response: " + response.body().daily.weather_code.length);

                    System.out.println("Response: " + hourly.get(currentHour).weatherCode);
                    System.out.println("Response DAILY: " + daily);
                    current.humidity = hourly.get(currentHour).humidity;
                    current.rain = hourly.get(currentHour).rain;
                    current.temperature = hourly.get(currentHour).temperature;
                    current.time = hourly.get(currentHour).time;
                    current.wind_speed = hourly.get(currentHour).windSpeed;
                    current.weatherDescription = hourly.get(currentHour).weatherDescription;
                    current.weatherCode = hourly.get(currentHour).weatherCode;


                    WeatherData data = new WeatherData();

                    System.out.println("DAILY Repo::: " + daily.size());

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

    public void getGeoCodingData(String cityName, GeocodingDataCallback callback) {
        Call<Geocoding> callAsync = geoApi.searchLocation(
                cityName, 10, "en", "json"
        );

        System.out.println("CITY NAME ::: "+cityName);

        callAsync.enqueue(new Callback<Geocoding>() {
            @Override
            public void onResponse(Call<Geocoding> call, Response<Geocoding> response) {
                System.out.println("GetData geocoding repository onResponse ::: "+response);
                if(response.isSuccessful()) {
                    Geocoding data = response.body();
                    System.out.println("GEOCODING  RESPONSE ::: "+response);
                    System.out.println("GEOCODING  RESPONSE body::: "+response.body().results);
                    if(data.results != null) {
                        System.out.println("GEOCODING ::: " + data.results[0].country);
                        System.out.println("GEOCODING ::: " + data.results[0].admin1);
                        System.out.println("GEOCODING ::: " + data.results[0].population);

                        callback.onWeatherDataReceived(data);
                    }
                    else{
                        callback.onFailure(new Exception("Failed to fetch data")); // Handle failure
                    }
                }
                else{
                    callback.onFailure(new Exception("Failed to fetch data")); // Handle failure
                }
            }

            @Override
            public void onFailure(Call<Geocoding> call, Throwable t) {
                System.out.println(t);
            }
        });

    }

    public void getAirQualityData(double latitude, double longitude, AirQaulityCallback callback) {
        // Hourly parameters you want to retrieve
        String hourly = "pm10,pm2_5,carbon_monoxide,nitrogen_dioxide,sulphur_dioxide,dust,uv_index";

        System.out.println("AIR QUALITY REPO CALL");
        Call<AirQuality> call = airApi.getAirQuality(latitude, longitude, hourly);
        call.enqueue(new Callback<AirQuality>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<AirQuality> call, Response<AirQuality> response) {
                System.out.println("GetData airQuality repository onResponse ::: "+response);

                System.out.println("AIR QUALITY ::: " + response);
                if (response.isSuccessful() && response.body() != null) {
                    AirQuality airQuality = response.body();
                    LocalTime currentTime = null;
                    currentTime = LocalTime.now();
                    // Extract the hour from the current time
                    int currentHour = currentTime.getHour();

                    System.out.println("AIR QUALITY ::: " + airQuality.hourly.time.length);
                    HourlyAirQuality airQuality24h = airQuality.hourly;
                    airQuality24h.time = Arrays.copyOfRange(airQuality24h.time, 0, currentHour + 24);
                    airQuality24h.pm10 = Arrays.copyOfRange(airQuality24h.pm10, 0, currentHour + 24);
                    airQuality24h.pm2_5 = Arrays.copyOfRange(airQuality24h.pm2_5, 0, currentHour + 24);
                    airQuality24h.carbon_monoxide = Arrays.copyOfRange(airQuality24h.carbon_monoxide, 0, currentHour + 24);
                    airQuality24h.sulphur_dioxide = Arrays.copyOfRange(airQuality24h.sulphur_dioxide, 0, currentHour + 24);
                    airQuality24h.nitrogen_dioxide = Arrays.copyOfRange(airQuality24h.nitrogen_dioxide, 0, currentHour + 24);
                    airQuality24h.uv_index = Arrays.copyOfRange(airQuality24h.uv_index, 0, currentHour + 24);
                    airQuality24h.dust = Arrays.copyOfRange(airQuality24h.dust, 0, currentHour + 24);

                    airQuality24h.airDescription = new String[airQuality24h.time.length];
                    airQuality24h.airCode = new int[airQuality24h.time.length];

                    HourlyAirQuality calculatedCodes = calculateCodes(airQuality24h);


                    System.out.println("AIR QUALITY time length: " + calculatedCodes.time.length + " airDescription length: " + calculatedCodes.airDescription.length);

                    List<HourlyAirData> data = mapToHourlyAirData(calculatedCodes);

                    System.out.println("AIR QUALITY MAPPER ::: " + data.size());
                    callback.onAirDataReceived(data);
                    // Handle the response data
                } else {
                    callback.onFailure(new Exception("Failed to fetch data")); // Handle failure
                }
            }

            @Override
            public void onFailure(Call<AirQuality> call, Throwable t) {
                System.out.println("FAILURE AIR QUALITY " + t);
                callback.onFailure(new Exception("Failed to fetch data")); // Handle failure
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<YearClimate> getCLimateChangeData() {

        Gson gson = new Gson();

        ClimateChange data = gson.fromJson(ClimateChangeData.five_years, ClimateChange.class);

        List<YearClimate> result  = mapToYearClimate(data);

        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<YearClimate>
    mapToYearClimate(ClimateChange data) {
        List<YearClimate> result = new ArrayList<>();
        String[] dataYears = Arrays.stream(data.daily.time).map(it -> {
            String[] parts = it.split("-");
            return parts[0];
        }).toArray(String[]::new);
        String[] years = Arrays.stream(dataYears).distinct().toArray(String[]::new);
        List<ClimateChange> climateYears = new ArrayList<>();
        int start = 0;
        int end = 0;

        for (int i = 0; i < years.length; i++) {
            int finalI = i;
            String[] time = Arrays.stream(data.daily.time).filter(it -> it.contains(years[finalI])).toArray(String[]::new);
            end = start + time.length;
            double[] minTemp = Arrays.stream(data.daily.minTemp, start, end).toArray();
            double[] maxTemp = Arrays.stream(data.daily.maxTemp, start, end).toArray();
            double[] windSpeed = Arrays.stream(data.daily.maxWindSpeed, start, end).toArray();
            double[] precipitation = Arrays.stream(data.daily.precipitation, start, end).toArray();

            YearClimate year = new YearClimate();
            DayClimate[] days = new DayClimate[time.length];
            for (int j = 0; j < time.length; j++) {
                DayClimate day = new DayClimate();
                day.time = time[j];
                day.maxTemp = maxTemp[j];
                day.minTemp = minTemp[j];
                day.precipitation = precipitation[j];
                day.windSpeed = windSpeed[j];
                days[j] = day;
            }

            year.days = days;
//            OptionalDouble maxTemperature = Arrays.stream(maxTemp).max();
////            if(maxTemperature.isPresent())
            year.maxTemp = Arrays.stream(maxTemp).max().getAsDouble();
            year.minTemp = Arrays.stream(minTemp).min().getAsDouble();
            year.maxWindSpeed = Arrays.stream(windSpeed).max().getAsDouble();
            year.totalPrecipitaiton = Arrays.stream(precipitation).sum();
            result.add(year);
            start = end;

        }
        return result;

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

        for (int i = 0; i < response.daily.time.length; i++) {
            DailyWeatherData day = new DailyWeatherData();
            String[] time = response.daily.time[i].split("T");
            day.date = time[0];
            day.time = time[0];
            day.weatherCode = calculateWeatherCode(response.daily.weather_code[i]);
            day.max_temperature = response.daily.temperature_max[i];
            day.min_temperature = response.daily.temperature_min[i];
            day.sunrise = response.daily.sunrise[i];
            day.sunset = response.daily.sunset[i];
            day.weatherDescription = calculateDescription(response.daily.weather_code[i]);
            daily.add(day);
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

    private HourlyAirQuality calculateCodes(HourlyAirQuality airQuality24h) {

        for (int i = 0; i < airQuality24h.time.length; i++) {
            int bad = 0;
            String badIndicator = "";
            if (airQuality24h.pm2_5[i] > 25) {
                bad = 1;
                badIndicator += " PM2.5";
            }
            if (airQuality24h.pm10[i] > 153) {
                bad = 1;
                badIndicator += " PM10";
            }
            if (airQuality24h.carbon_monoxide[i] > 150) {
                bad = 1;
                badIndicator += " CO2";
            }
            if (airQuality24h.nitrogen_dioxide[i] > 20) {
                bad = 1;
                badIndicator += " NO2";
            }
            if (airQuality24h.sulphur_dioxide[i] > 20) {
                bad = 1;
                badIndicator += " SO2";
            }
            if (airQuality24h.uv_index[i] > 6) {
                bad = 1;
                badIndicator += " UV index";
            }
            if (airQuality24h.dust[i] > 1) {
                bad = 2;
                badIndicator += " dust";
            }
            if (bad == 1) {
                airQuality24h.airDescription[i] = badIndicator;
                airQuality24h.airCode[i] = R.drawable.air_bad;
            } else {
                String description = "Good air conditions";
                if(bad==2){
                    description+="\nbut dusty";
                }
                airQuality24h.airDescription[i] = description;
                airQuality24h.airCode[i] = R.drawable.goodair;
            }
        }
        return airQuality24h;
    }

    private List<HourlyAirData> mapToHourlyAirData(HourlyAirQuality data) {
        List<HourlyAirData> list = new ArrayList<>();
        for (int i = 0; i < data.time.length; i++) {
            HourlyAirData hour = new HourlyAirData();
            hour.airCode = data.airCode[i];
            hour.airDescription = data.airDescription[i];
            hour.carbon_monoxide = data.carbon_monoxide[i];
            hour.nitrogen_dioxide = data.nitrogen_dioxide[i];
            hour.sulphur_dioxide = data.sulphur_dioxide[i];
            hour.dust = data.dust[i];
            hour.uv_index = data.uv_index[i];
            hour.pm10 = data.pm10[i];
            hour.pm2_5 = data.pm2_5[i];
            hour.time = data.time[i];
            list.add(hour);
        }
        return list;

    }

}
