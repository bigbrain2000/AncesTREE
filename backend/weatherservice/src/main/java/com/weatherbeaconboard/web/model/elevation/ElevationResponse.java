package com.weatherbeaconboard.web.model.elevation;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;

public record ElevationResponse(

        @NotNull
        @JsonProperty("elevation")
        List<Double> elevation

) implements Serializable {

}
