package com.weatherbeaconboard.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class JwtProperties {

    @Value("${weatherbeaconboard.jwtSecret}")
    private String jwtSecret;

    @Value("${weatherbeaconboard.jwtExpirationMs}")
    private long jwtExpirationMs;
}
