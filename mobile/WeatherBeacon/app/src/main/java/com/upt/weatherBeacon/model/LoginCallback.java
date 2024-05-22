package com.upt.weatherBeacon.model;

import com.upt.weatherBeacon.data.remote.userRepository.dtos.LoginDto;

public interface LoginCallback {

    void onLoginDataReceived(String token);
    void onFailure(Throwable throwable);
}
