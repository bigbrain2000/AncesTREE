package com.weatherbeaconboard.service.converter;

import com.weatherbeaconboard.model.ConfirmationTokenEntity;
import com.weatherbeaconboard.web.model.ConfirmationToken;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static com.weatherbeaconboard.utils.DateUtils.getDateTime;
import static org.junit.jupiter.api.Assertions.*;

class ConfirmationTokenToConfirmationTokenEntityConverterTest {

    private final ConfirmationTokenToConfirmationTokenEntityConverter uut = new ConfirmationTokenToConfirmationTokenEntityConverter();

    @Test
    void convert_validConfirmationToken_returnsConfirmationTokenEntity() {
        final OffsetDateTime NOW = getDateTime();

        final ConfirmationTokenEntity expectedConfirmationTokenEntity = ConfirmationTokenEntity.builder()
                .id(1)
                .token("token")
                .tokenCreatedAt(NOW)
                .tokenExpiresAt(NOW.plusMinutes(30))
                .tokenConfirmedAt(NOW)
                .build();

        final ConfirmationToken expectedConfirmationToken = ConfirmationToken.builder()
                .id(1)
                .token("token")
                .tokenCreatedAt(NOW)
                .tokenExpiresAt(NOW.plusMinutes(30))
                .tokenConfirmedAt(NOW)
                .build();

        final ConfirmationTokenEntity actualConfirmationTokenEntity = uut.convert(expectedConfirmationToken);

        assertEquals(expectedConfirmationTokenEntity, actualConfirmationTokenEntity);
    }
}