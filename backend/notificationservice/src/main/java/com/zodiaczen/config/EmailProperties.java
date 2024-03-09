package com.zodiaczen.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class EmailProperties {

    @Value("${zodiaczen.mail.from}")
    private String applicationEmail;

}
