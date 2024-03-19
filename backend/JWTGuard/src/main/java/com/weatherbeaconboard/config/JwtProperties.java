package com.weatherbeaconboard.config;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class JwtProperties {

    public static final String JWT_SECRET = "Am abonament la bani Si banii la mine.. Cat as cheltui-mi raman Conturile pline.." +
            " Am abonament la bani Ei vin permanent..Sunt asigurat pe viata AM ABONAMENT";

    public static final long JWT_EXPIRATION_MS = 86400000;

    private JwtProperties() {
        throw new IllegalStateException("Reflection should not be used to access this class");
    }
}

