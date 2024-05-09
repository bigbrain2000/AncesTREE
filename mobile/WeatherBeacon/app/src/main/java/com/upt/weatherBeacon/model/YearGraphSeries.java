package com.upt.weatherBeacon.model;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.List;

public class YearGraphSeries {
    public int year;
    public List<LineGraphSeries<DataPoint>> series;
}
