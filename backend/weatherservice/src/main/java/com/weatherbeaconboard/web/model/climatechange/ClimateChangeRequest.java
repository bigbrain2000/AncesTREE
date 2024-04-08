package com.weatherbeaconboard.web.model.climatechange;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Builder
public record ClimateChangeRequest(

@NotNull
@JsonProperty("latitude") List<Double> latitude,

@NotNull
@JsonProperty("longitude") List<Double> longitude,

@NotNull
@JsonProperty("start_date") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate startDate,

@NotNull
@JsonProperty("end_date") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate endDate,

@NotNull
@JsonProperty("models") List<String> models,

@NotNull
@JsonProperty("daily") List<String> daily,

@JsonProperty("temperature_unit") String temperatureUnit,
@JsonProperty("wind_speed_unit") String windSpeedUnit,
@JsonProperty("precipitation_unit") String precipitationUnit,
@JsonProperty("timeformat") String timeFormat,
@JsonProperty("disable_bias_correction") Boolean disableBiasCorrection,
@JsonProperty("cell_selection") String cellSelection

        )implements Serializable{

        }
