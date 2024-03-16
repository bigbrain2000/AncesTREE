package com.weatherbeaconboard.web.model;

import lombok.Builder;

@Builder
public record AuthenticationUserResponse(

        String jwtToken,

        String username,

        String tokenType

) {
}
