package com.zodiaczen.service.converter;

import com.zodiaczen.exceptions.ConversionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NullSafeConverter {

    private final ConversionService conversionService;

    public NullSafeConverter(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    public <S, T> T convert(S source, Class<T> targetType) {
        if (source == null || targetType == null) {
            throw new ConversionException("Target type is null");
        }

        return conversionService.convert(source, targetType);
    }
}
