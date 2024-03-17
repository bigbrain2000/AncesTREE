package com.weatherbeaconboard.web.model;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record JWTResponse(

        String accessToken

) implements Serializable {

}
