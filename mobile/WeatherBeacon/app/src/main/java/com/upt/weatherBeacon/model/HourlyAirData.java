package com.upt.weatherBeacon.model;

import com.google.gson.annotations.SerializedName;

public class HourlyAirData {
    public String time;
    public double pm10;
    public double pm2_5;
    public double carbon_monoxide;
    public double nitrogen_dioxide;
    public double sulphur_dioxide;
    public int dust;
    public double uv_index;
    public String airDescription;
    public int airCode;
}
