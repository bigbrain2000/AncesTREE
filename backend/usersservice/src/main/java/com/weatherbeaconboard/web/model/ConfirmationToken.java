package com.weatherbeaconboard.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.weatherbeaconboard.model.ConfirmationTokenEntity;
import lombok.Builder;

import java.io.Serializable;
import java.time.OffsetDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static com.weatherbeaconboard.utils.DateUtils.DATE_TIME_PATTERN;

/**
 * Web model for a {@link ConfirmationTokenEntity}.
 */
@Builder
public record ConfirmationToken(

        Integer id,

        String token,

@JsonFormat(shape = STRING, pattern = DATE_TIME_PATTERN)
        OffsetDateTime tokenCreatedAt,

@JsonFormat(shape = STRING, pattern = DATE_TIME_PATTERN)
        OffsetDateTime tokenExpiresAt,

@JsonFormat(shape = STRING, pattern = DATE_TIME_PATTERN)
        OffsetDateTime tokenConfirmedAt

                )implements Serializable{

                }
