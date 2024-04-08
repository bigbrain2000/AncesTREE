package com.weatherbeaconboard.web.model.flood;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Builder
public record FloodRequest(

@NotNull
@JsonProperty("latitude") double latitude,

@NotNull
@JsonProperty("longitude") double longitude,

@JsonProperty("daily") String[]dailyVariables,
@JsonProperty("timeformat") String timeFormat,
@JsonProperty("past_days") Integer pastDays,
@JsonProperty("forecast_days") Integer forecastDays,
@JsonProperty("start_date") String startDate,
@JsonProperty("end_date") String endDate,
@JsonProperty("ensemble") Boolean ensemble,
@JsonProperty("cell_Selection") String cellSelection

        )implements Serializable{

@Override
public boolean equals(Object o){
        if(this==o)return true;
        if(o==null||getClass()!=o.getClass())return false;
        FloodRequest that=(FloodRequest)o;
        return Double.compare(latitude,that.latitude)==0&&Double.compare(longitude,that.longitude)==0&&
        Objects.equals(pastDays,that.pastDays)&&Objects.equals(forecastDays,that.forecastDays)&&
        ensemble==that.ensemble&&Arrays.equals(dailyVariables,that.dailyVariables)&&
        Objects.equals(timeFormat,that.timeFormat)&&Objects.equals(startDate,that.startDate)&&
        Objects.equals(endDate,that.endDate)&&Objects.equals(cellSelection,that.cellSelection);
        }

@Override
public int hashCode(){
        int result=Objects.hash(latitude,longitude,timeFormat,pastDays,forecastDays,startDate,endDate,ensemble,cellSelection);
        result=31*result+Arrays.hashCode(dailyVariables);
        return result;
        }

@Override
public String toString(){
        return"FloodRequest{"+
        "latitude="+latitude+
        ", longitude="+longitude+
        ", dailyVariables="+Arrays.toString(dailyVariables)+
        ", timeFormat='"+timeFormat+'\''+
        ", pastDays="+pastDays+
        ", forecastDays="+forecastDays+
        ", startDate='"+startDate+'\''+
        ", endDate='"+endDate+'\''+
        ", ensemble="+ensemble+
        ", cellSelection='"+cellSelection+'\''+
        '}';
        }
        }
