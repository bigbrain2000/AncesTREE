package com.weatherbeaconboard.web.model.flood;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record DailyParameters(

@JsonProperty("riverDischarge") String riverDischarge,
@JsonProperty("riverDischargeMean") String riverDischargeMean,
@JsonProperty("riverDischargeMedian") String riverDischargeMedian,
@JsonProperty("riverDischargeMax") String riverDischargeMax,
@JsonProperty("riverDischargeMin") String riverDischargeMin,
@JsonProperty("riverDischargeP25") String riverDischargeP25,
@JsonProperty("riverDischargeP75") String riverDischargeP75

        )implements Serializable{

        }
