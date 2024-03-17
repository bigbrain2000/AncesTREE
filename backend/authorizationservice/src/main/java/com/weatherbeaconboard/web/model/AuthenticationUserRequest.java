package com.weatherbeaconboard.web.model;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record AuthenticationUserRequest(

        String username,

        String password

) implements Serializable {

}
