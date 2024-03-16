package com.weatherbeaconboard.web.model.forecast;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Builder
@Nullable
public record ForecastWeatherRequest(

        @NotNull
        @JsonProperty("latitude") Double latitude,

        @NotNull
        @JsonProperty("longitude") Double longitude,

        @JsonProperty("elevation") Double elevation,
        @JsonProperty("hourly") String[] hourly,
        @JsonProperty("daily") String[] daily,
        @JsonProperty("current") String[] current,
        @JsonProperty("temperature_unit") String temperatureUnit,
        @JsonProperty("wind_speed_unit") String windSpeedUnit,
        @JsonProperty("precipitation_unit") String precipitationUnit,
        @JsonProperty("timeformat") String timeFormat,
        @JsonProperty("timezone") String timezone,
        @JsonProperty("past_days") Integer pastDays,
        @JsonProperty("forecast_days") Integer forecastDays,
        @JsonProperty("start_date") String startDate,
        @JsonProperty("end_date") String endDate,
        @JsonProperty("start_hour") Integer startHour,
        @JsonProperty("end_hour") Integer endHour,
        @JsonProperty("models") String[] models,
        @JsonProperty("cell_selection") String cellSelection,
        @JsonProperty("forecast_hours") Integer forecastHours,
        @JsonProperty("forecast_minutely_15") Integer forecastMinutely15,
        @JsonProperty("past_hours") Integer pastHours,
        @JsonProperty("past_minutely_15") Integer pastMinutely15,
        @JsonProperty("start_minutely_15") String startMinutely15,
        @JsonProperty("end_minutely_15") String endMinutely15

) implements Serializable {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForecastWeatherRequest that = (ForecastWeatherRequest) o;
        return Objects.equals(latitude, that.latitude) && Objects.equals(longitude, that.longitude) &&
                Objects.equals(elevation, that.elevation) && Arrays.equals(hourly, that.hourly) &&
                Arrays.equals(daily, that.daily) && Arrays.equals(current, that.current) &&
                Objects.equals(temperatureUnit, that.temperatureUnit) && Objects.equals(windSpeedUnit, that.windSpeedUnit)
                && Objects.equals(precipitationUnit, that.precipitationUnit) && Objects.equals(timeFormat, that.timeFormat)
                && Objects.equals(timezone, that.timezone) && Objects.equals(pastDays, that.pastDays) &&
                Objects.equals(forecastDays, that.forecastDays) && Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) && Objects.equals(startHour, that.startHour) &&
                Objects.equals(endHour, that.endHour) && Arrays.equals(models, that.models) &&
                Objects.equals(cellSelection, that.cellSelection) && Objects.equals(forecastHours, that.forecastHours) &&
                Objects.equals(forecastMinutely15, that.forecastMinutely15) && Objects.equals(pastHours, that.pastHours) &&
                Objects.equals(pastMinutely15, that.pastMinutely15) && Objects.equals(startMinutely15, that.startMinutely15) &&
                Objects.equals(endMinutely15, that.endMinutely15);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(latitude, longitude, elevation, temperatureUnit, windSpeedUnit, precipitationUnit,
                timeFormat, timezone, pastDays, forecastDays, startDate, endDate, startHour, endHour, cellSelection,
                forecastHours, forecastMinutely15, pastHours, pastMinutely15, startMinutely15, endMinutely15);

        result = 31 * result + Arrays.hashCode(hourly);
        result = 31 * result + Arrays.hashCode(daily);
        result = 31 * result + Arrays.hashCode(current);
        result = 31 * result + Arrays.hashCode(models);
        return result;
    }

    @Override
    public String toString() {
        return "ForecastWeatherRequest{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", elevation=" + elevation +
                ", hourly=" + Arrays.toString(hourly) +
                ", daily=" + Arrays.toString(daily) +
                ", current=" + Arrays.toString(current) +
                ", temperatureUnit='" + temperatureUnit + '\'' +
                ", windSpeedUnit='" + windSpeedUnit + '\'' +
                ", precipitationUnit='" + precipitationUnit + '\'' +
                ", timeFormat='" + timeFormat + '\'' +
                ", timezone='" + timezone + '\'' +
                ", pastDays=" + pastDays +
                ", forecastDays=" + forecastDays +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", startHour=" + startHour +
                ", endHour=" + endHour +
                ", models=" + Arrays.toString(models) +
                ", cellSelection='" + cellSelection + '\'' +
                ", forecastHours=" + forecastHours +
                ", forecastMinutely15=" + forecastMinutely15 +
                ", pastHours=" + pastHours +
                ", pastMinutely15=" + pastMinutely15 +
                ", startMinutely15='" + startMinutely15 + '\'' +
                ", endMinutely15='" + endMinutely15 + '\'' +
                '}';
    }
}

