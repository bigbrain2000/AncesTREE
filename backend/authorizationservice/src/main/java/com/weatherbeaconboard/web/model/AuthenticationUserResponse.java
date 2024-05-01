package com.weatherbeaconboard.web.model;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record AuthenticationUserResponse(

        String jwtToken,

        String username,

        String tokenType

        )implements Serializable{

        }
