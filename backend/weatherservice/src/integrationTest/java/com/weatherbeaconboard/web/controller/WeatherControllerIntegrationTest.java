package com.weatherbeaconboard.web.controller;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.http.ContentType.JSON;

public class WeatherControllerIntegrationTest {

    private static final String GET_FORECAST_WEATHER_URL = "/v1/forecast";
    private static final String GET_FLOOD_STATISTICS_URL = "/v1/flood";
    private static final String GET_ELEVATION_STATISTICS_URL = "/v1/elevation";
    private static final String GET_AIR_QUALITY_STATISTICS_URL = "/v1/air-quality";
    private static final String GET_CLIMATE_STATISTICS_URL = "/v1/climate";

    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8084;
    }

    @Test
    void getForecastWeather_providingRequiredParams_returnsStatusCode200() {
        getLongitudeAndLatitudeParams()
                .when()
                .get(GET_FORECAST_WEATHER_URL)
                .then()
                .statusCode(200);
    }

    @Test
    void getFloodStatistics_providingRequiredParams_returnsStatusCode200() {
        getLongitudeAndLatitudeParams()
                .param("daily", List.of("river_discharge"))
                .when()
                .get(GET_FLOOD_STATISTICS_URL)
                .then()
                .statusCode(200);
    }

    @Test
    void getElevationStatistics_providingRequiredParams_returnsStatusCode200() {
        getLongitudeAndLatitudeParams()
                .when()
                .get(GET_ELEVATION_STATISTICS_URL)
                .then()
                .statusCode(200);
    }

    @Test
    void getAirQualityStatistics_providingRequiredParams_returnsStatusCode200() {
        getLongitudeAndLatitudeParams()
                .when()
                .get(GET_AIR_QUALITY_STATISTICS_URL)
                .then()
                .statusCode(200);
    }

    @Test
    void getClimateStatistics_providingRequiredParams_returnsStatusCode200() {
        getLongitudeAndLatitudeParams()
                .param("start_date", "2024-01-01")
                .param("end_date", "2024-12-31")
                .param("models", "CMCC_CM2_VHR4,FGOALS_f3_H,HiRAM_SIT_HR,MRI_AGCM3_2_S,EC_Earth3P_HR,MPI_ESM1_2_XR,NICAM16_8S")
                .param("daily", "temperature_2m_max")
                .when()
                .get(GET_CLIMATE_STATISTICS_URL)
                .then()
                .statusCode(200);
    }

    private static RequestSpecification getLongitudeAndLatitudeParams() {
        return RestAssured.given()
                .contentType(JSON)
                .param(LATITUDE, 44.11667)
                .param(LONGITUDE, 24.35);
    }
}
