package com.weatherbeaconboard.web.model.airquality;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record HourlyParameter(

        @JsonProperty("pm10") boolean pm10,
        @JsonProperty("pm2_5") boolean pm2_5,
        @JsonProperty("carbon_monoxide") boolean carbonMonoxide,
        @JsonProperty("nitrogen_dioxide") boolean nitrogenDioxide,
        @JsonProperty("sulphur_dioxide") boolean sulphurDioxide,
        @JsonProperty("ozone") boolean ozone,
        @JsonProperty("ammonia") boolean ammonia,
        @JsonProperty("aerosol_optical_depth") boolean aerosolOpticalDepth,
        @JsonProperty("dust") boolean dust,
        @JsonProperty("uv_index") boolean uvIndex,
        @JsonProperty("uv_index_clear_sky") boolean uvIndexClearSky,
        @JsonProperty("alder_pollen") boolean alderPollen,
        @JsonProperty("birch_pollen") boolean birchPollen,
        @JsonProperty("grass_pollen") boolean grassPollen,
        @JsonProperty("mugwort_pollen") boolean mugwortPollen,
        @JsonProperty("olive_pollen") boolean olivePollen,
        @JsonProperty("ragweed_pollen") boolean ragweedPollen,
        @JsonProperty("european_aqi") boolean europeanAQI,
        @JsonProperty("european_aqi_pm2_5") boolean europeanAQIPM2_5,
        @JsonProperty("european_aqi_pm10") boolean europeanAQIPM10,
        @JsonProperty("european_aqi_nitrogen_dioxide") boolean europeanAQINitrogenDioxide,
        @JsonProperty("european_aqi_ozone") boolean europeanAQIOzone,
        @JsonProperty("european_aqi_sulphur_dioxide") boolean europeanAQISulphurDioxide

) implements Serializable {

}
