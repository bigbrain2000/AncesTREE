package com.weatherbeaconboard.service.users;

import com.weatherbeaconboard.web.model.UserDetailsResponse;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

public interface UserService {

    /**
     * Gwt user details under {@link UserDetails} object by calling the users service.
     *
     * @param username the username for which to retrieve the {@link UserDetails} object
     * @return an object of type {@link UserDetails}, that represents the user details
     */
    Mono<UserDetailsResponse> getUserDetails(@NotBlank String username);
}
