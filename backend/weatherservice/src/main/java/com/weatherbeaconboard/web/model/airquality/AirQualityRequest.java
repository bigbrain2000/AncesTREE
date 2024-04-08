package com.weatherbeaconboard.web.model.airquality;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Builder
public record AirQualityRequest(

@NotNull
@JsonProperty("latitude") double latitude,

@NotNull
@JsonProperty("longitude") double longitude,

@JsonProperty("hourly") String[]hourly,
@JsonProperty("current") String[]current,
@JsonProperty("domains") String domains,
@JsonProperty("timeformat") String timeformat,
@JsonProperty("timezone") String timezone,
@JsonProperty("past_days") Integer pastDays,
@JsonProperty("forecast_days") Integer forecastDays,
@JsonProperty("forecast_hours") Integer forecastHours,
@JsonProperty("past_hours") Integer pastHours,
@JsonProperty("start_date") String startDate,
@JsonProperty("end_date") String endDate,
@JsonProperty("start_hour") String startHour,
@JsonProperty("end_hour") String endHour,
@JsonProperty("cell_selection") String cellSelection

        )implements Serializable{
@Override
public boolean equals(Object o){
        if(this==o)return true;
        if(o==null||getClass()!=o.getClass())return false;
        AirQualityRequest that=(AirQualityRequest)o;
        return Double.compare(latitude,that.latitude)==0&&Double.compare(longitude,that.longitude)==0&&
        Arrays.equals(hourly,that.hourly)&&Arrays.equals(current,that.current)&&
        Objects.equals(domains,that.domains)&&Objects.equals(timeformat,that.timeformat)&&
        Objects.equals(timezone,that.timezone)&&Objects.equals(pastDays,that.pastDays)&&
        Objects.equals(forecastDays,that.forecastDays)&&Objects.equals(forecastHours,that.forecastHours)&&
        Objects.equals(pastHours,that.pastHours)&&Objects.equals(startDate,that.startDate)&&
        Objects.equals(endDate,that.endDate)&&Objects.equals(startHour,that.startHour)&&
        Objects.equals(endHour,that.endHour)&&Objects.equals(cellSelection,that.cellSelection);
        }

@Override
public int hashCode(){
        int result=Objects.hash(latitude,longitude,domains,timeformat,timezone,pastDays,forecastDays,
        forecastHours,pastHours,startDate,endDate,startHour,endHour,cellSelection);
        result=31*result+Arrays.hashCode(hourly);
        result=31*result+Arrays.hashCode(current);
        return result;
        }

@Override
public String toString(){
        return"AirQualityRequest{"+
        "latitude="+latitude+
        ", longitude="+longitude+
        ", hourly="+Arrays.toString(hourly)+
        ", current="+Arrays.toString(current)+
        ", domains='"+domains+'\''+
        ", timeformat='"+timeformat+'\''+
        ", timezone='"+timezone+'\''+
        ", pastDays="+pastDays+
        ", forecastDays="+forecastDays+
        ", forecastHours="+forecastHours+
        ", pastHours="+pastHours+
        ", startDate='"+startDate+'\''+
        ", endDate='"+endDate+'\''+
        ", startHour='"+startHour+'\''+
        ", endHour='"+endHour+'\''+
        ", cellSelection='"+cellSelection+'\''+
        '}';
        }
        }
