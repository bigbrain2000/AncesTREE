package com.weatherbeaconboard.utils;

import com.weatherbeaconboard.web.model.EmailRequest;

public interface EmailRequestUtil {
    static EmailRequest buildEmailRequest() {
        return EmailRequest.builder()
                .to("alex@yahoo.com")
                .subject("register")
                .body("body")
                .build();
    }
}
