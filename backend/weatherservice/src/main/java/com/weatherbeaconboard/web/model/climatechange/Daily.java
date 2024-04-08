package com.weatherbeaconboard.web.model.climatechange;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public record Daily(

@JsonProperty("temperature_2m_max_CMCC_CM2_VHR4") List<Object> temperature2mMaxCMCCCM2VHR4,
@JsonProperty("temperature_2m_max_FGOALS_f3_H") List<Object> temperature2mMaxFGOALSF3H,
@JsonProperty("temperature_2m_max_NICAM16_8S") List<Object> temperature2mMaxNICAM168S,
@JsonProperty("temperature_2m_max_MRI_AGCM3_2_S") List<Object> temperature2mMaxMRIAGCM32S,
@JsonProperty("temperature_2m_max_MPI_ESM1_2_XR") List<Object> temperature2mMaxMPIESM12XR,
@JsonProperty("temperature_2m_max_HiRAM_SIT_HR") List<Object> temperature2mMaxHiRAMSITHR,
@JsonProperty("time") List<String> time,
@JsonProperty("temperature_2m_max_EC_Earth3P_HR") List<Object> temperature2mMaxECEarth3PHR

        )implements Serializable{

        }