package com.weatherbeaconboard.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class EmailProperties {

    @Value("${weatherbeaconboard.mail.from}")
    private String applicationEmail;

}
