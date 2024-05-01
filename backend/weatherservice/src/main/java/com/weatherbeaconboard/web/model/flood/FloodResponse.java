package com.weatherbeaconboard.web.model.flood;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record FloodResponse(

@JsonProperty("latitude")
        double latitude,

@JsonProperty("longitude")
        double longitude,

@JsonProperty("generationtime_ms")
        double generationTimeMs,

@JsonProperty("daily")
        Object daily,

@JsonProperty("daily_units")
        Object dailyUnits

                )implements Serializable{

                }
