package com.upt.weatherBeacon.data.remote.userRepository.dtos;

import com.google.gson.annotations.SerializedName;

public class LoginDto {
    @SerializedName("jwtToken")
    public String token;
}
