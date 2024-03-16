package com.weatherbeaconboard.service.security;

import com.weatherbeaconboard.service.UserDetailsFetcher;
import com.weatherbeaconboard.service.users.UserService;
import com.weatherbeaconboard.web.model.UserDetailsResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class CustomUserDetailsFetcher implements UserDetailsFetcher {

    private final UserService userService;

    @Override
    public Mono<UserDetailsResponse> getUserDetails(String username) {
        return userService.getUserDetails(username);
    }
}
