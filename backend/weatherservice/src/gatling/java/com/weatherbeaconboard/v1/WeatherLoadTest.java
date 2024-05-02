package com.weatherbeaconboard.v1;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class WeatherLoadTest extends Simulation {

    private static final String GET_FORECAST_WEATHER_URL = "/v1/forecast";
    private static final String GET_FLOOD_STATISTICS_URL = "/v1/flood";
    private static final String GET_ELEVATION_STATISTICS_URL = "/v1/elevation";
    private static final String GET_AIR_QUALITY_STATISTICS_URL = "/v1/air-quality";
    private static final String GET_CLIMATE_STATISTICS_URL = "/v1/climate";

    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";

    private static final int EXPECTED_OK_STATUS_CODE = 200;

    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8084")
            .contentTypeHeader("application/json")
            .acceptHeader("application/json");

    {
        String randomLatitudeValue = String.valueOf(ThreadLocalRandom.current().nextDouble(44.0, 45.0));
        String randomLongitudeValue = String.valueOf(ThreadLocalRandom.current().nextDouble(24.0, 25.0));


        final ScenarioBuilder scn = scenario("Load Test Forecast Endpoint")
                .exec(http("Forecast Request")
                        .get(GET_FORECAST_WEATHER_URL)
                        .queryParam(LATITUDE, randomLatitudeValue)
                        .queryParam(LONGITUDE, randomLongitudeValue)
                        .check(status().is(EXPECTED_OK_STATUS_CODE)))
                .pause(5)  // pause for a second between requests
                .exec(http("Flood Statistics Request")
                        .get(GET_FLOOD_STATISTICS_URL)
                        .queryParam(LATITUDE, randomLatitudeValue)
                        .queryParam(LONGITUDE, randomLongitudeValue)
                        .queryParam("daily", "river_discharge")
                        .queryParam("daily", "river_discharge_mean")
                        .queryParam("daily", "river_discharge_median")
                        .queryParam("daily", "river_discharge_max")
                        .queryParam("daily", "river_discharge_min")
                        .queryParam("daily", "river_discharge_p25")
                        .queryParam("daily", "river_discharge_p75")
                        .queryParam("timeformat", "iso8601")
                        .queryParam("past_days", "30")
                        .queryParam("forecast_days", "210")
                        .queryParam("ensemble", "true")
                        .queryParam("cell_selection", "nearest")
                        .check(status().is(EXPECTED_OK_STATUS_CODE)))
                .pause(5)   // pause for a second between requests
                .exec(http("Elevation Statistics Request")
                        .get(GET_ELEVATION_STATISTICS_URL)
                        .queryParam(LATITUDE, randomLatitudeValue)
                        .queryParam(LONGITUDE, randomLongitudeValue)
                        .check(status().is(EXPECTED_OK_STATUS_CODE)))
                .pause(5)   // pause for a second between requests
                .exec(http("Air Quality Statistics Request")
                        .get(GET_AIR_QUALITY_STATISTICS_URL)
                        .queryParam(LATITUDE, randomLatitudeValue)
                        .queryParam(LONGITUDE, randomLongitudeValue)
                        .check(status().is(EXPECTED_OK_STATUS_CODE)))
                .pause(5)   // pause for a second between requests
                .exec(http("Climate Statistics Request")
                        .get(GET_CLIMATE_STATISTICS_URL)
                        .queryParam(LATITUDE, randomLatitudeValue)
                        .queryParam(LONGITUDE, randomLongitudeValue)
                        .queryParam("start_date", "2024-01-01")
                        .queryParam("end_date", "2024-12-31")
                        .queryParam("models", "CMCC_CM2_VHR4,FGOALS_f3_H,HiRAM_SIT_HR,MRI_AGCM3_2_S,EC_Earth3P_HR,MPI_ESM1_2_XR,NICAM16_8S")
                        .queryParam("daily", "temperature_2m_max")
                        .check(status().is(EXPECTED_OK_STATUS_CODE)))
                .pause(5);

        setUp(scn
                .injectClosed(rampConcurrentUsers(0).to(10).during(Duration.of(1, ChronoUnit.MINUTES)))
                .protocols(httpProtocol)
        )
                .maxDuration(Duration.of(3, ChronoUnit.MINUTES))
                .assertions(
                        details("Forecast Request").responseTime().percentile(99).lte(3_000),
                        details("Flood Statistics Request").responseTime().percentile(99).lte(3_000),
                        details("Elevation Statistics Request").responseTime().percentile(99).lte(3_000),
                        details("Air Quality Statistics Request").responseTime().percentile(99).lte(3_000),
                        details("Climate Statistics Request").responseTime().percentile(99).lte(3_000)
                );
    }
}