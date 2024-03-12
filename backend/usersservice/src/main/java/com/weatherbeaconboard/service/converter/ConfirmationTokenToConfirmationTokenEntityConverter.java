package com.weatherbeaconboard.service.converter;

import com.weatherbeaconboard.model.ConfirmationTokenEntity;
import com.weatherbeaconboard.web.model.ConfirmationToken;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ConfirmationTokenToConfirmationTokenEntityConverter implements Converter<ConfirmationToken, ConfirmationTokenEntity> {

    @Override
    public ConfirmationTokenEntity convert(ConfirmationToken source) {
        return ConfirmationTokenEntity.builder()
                .id(source.id())
                .token(source.token())
                .tokenCreatedAt(source.tokenCreatedAt())
                .tokenExpiresAt(source.tokenExpiresAt())
                .tokenConfirmedAt(source.tokenConfirmedAt())
                .build();
    }
}
