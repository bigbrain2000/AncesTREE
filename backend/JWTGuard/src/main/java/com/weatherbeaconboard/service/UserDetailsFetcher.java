package com.weatherbeaconboard.service;

import com.weatherbeaconboard.web.model.UserDetailsResponse;
import reactor.core.publisher.Mono;

public interface UserDetailsFetcher {
    Mono<UserDetailsResponse> getUserDetails(String username);
}