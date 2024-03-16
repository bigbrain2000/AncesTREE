package com.weatherbeaconboard.utils;

import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class used for defining the util methods/constants for dates.
 */
public class DateUtils {

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSX";

    private DateUtils() {
    }

    /**
     * Obtains the current date-time from the system clock in the default time-zone and return a OffsetDateTime having
     * the DATE_TIME_PATTERN.
     */
    @NotNull
    public static OffsetDateTime getDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        OffsetDateTime now = OffsetDateTime.now();
        String formattedNow = now.format(formatter);
        return OffsetDateTime.parse(formattedNow, formatter);
    }
}