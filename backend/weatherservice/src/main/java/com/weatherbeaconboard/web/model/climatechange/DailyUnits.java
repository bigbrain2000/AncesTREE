package com.weatherbeaconboard.web.model.climatechange;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record DailyUnits(
		@JsonProperty("temperature_2m_max_CMCC_CM2_VHR4") String temperature2mMaxCMCCCM2VHR4,
		@JsonProperty("temperature_2m_max_FGOALS_f3_H") String temperature2mMaxFGOALSF3H,
		@JsonProperty("temperature_2m_max_NICAM16_8S") String temperature2mMaxNICAM168S,
		@JsonProperty("temperature_2m_max_MRI_AGCM3_2_S") String temperature2mMaxMRIAGCM32S,
		@JsonProperty("temperature_2m_max_MPI_ESM1_2_XR") String temperature2mMaxMPIESM12XR,
		@JsonProperty("temperature_2m_max_HiRAM_SIT_HR") String temperature2mMaxHiRAMSITHR,
		@JsonProperty("time") String time,
		@JsonProperty("temperature_2m_max_EC_Earth3P_HR") String temperature2mMaxECEarth3PHR

) implements Serializable {

}
