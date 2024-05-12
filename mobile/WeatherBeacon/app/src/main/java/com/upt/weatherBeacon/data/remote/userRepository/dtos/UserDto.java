package com.upt.weatherBeacon.data.remote.userRepository.dtos;

import com.google.gson.annotations.SerializedName;

public class UserDto {
    @SerializedName("username")
    public String username;
    @SerializedName("firstName")
    public String firstName;
    @SerializedName("lastName")
    public String lastName;
    @SerializedName("email")
    public String email;
    @SerializedName("address")
    public String address;
    @SerializedName("phoneNumber")
    public String phoneNumber;
}
