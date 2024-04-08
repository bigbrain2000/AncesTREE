package com.weatherbeaconboard.web.model.elevation;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public record ElevationRequest(

@NotNull
@JsonProperty("latitude") List<Double> latitude,

@NotNull
@JsonProperty("longitude") List<Double> longitude

        )implements Serializable{

        }
