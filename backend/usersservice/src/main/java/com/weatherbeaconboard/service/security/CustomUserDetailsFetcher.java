package com.weatherbeaconboard.service.security;

import com.weatherbeaconboard.service.UserDetailsFetcher;
import com.weatherbeaconboard.service.converter.NullSafeConverter;
import com.weatherbeaconboard.service.user.UserService;
import com.weatherbeaconboard.web.model.UserDetailsResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class CustomUserDetailsFetcher implements UserDetailsFetcher {

    private final UserService userService;
    private NullSafeConverter converter;

    @Override
    public Mono<UserDetailsResponse> getUserDetails(String username) {
        final UserDetails foundUserDetails = userService.loadUserByUsername(username);
        final UserDetailsResponse userDetailsResponse = converter.convert(foundUserDetails, UserDetailsResponse.class);

        return Mono.just(userDetailsResponse);
    }
}
