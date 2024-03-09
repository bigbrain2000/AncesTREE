package com.zodiaczen.web.model;

import lombok.Builder;

@Builder
public record AuthenticationUserRequest(

        String username,
        String password
        
) {
}
