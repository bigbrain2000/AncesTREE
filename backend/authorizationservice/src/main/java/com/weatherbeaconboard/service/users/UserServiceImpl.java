package com.weatherbeaconboard.service.users;

import com.weatherbeaconboard.client.UserServiceAsyncClient;
import com.weatherbeaconboard.exceptions.UsersServiceExceptions;
import com.weatherbeaconboard.web.model.UserDetailsResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserServiceAsyncClient userServiceAsyncClient;

    public Mono<UserDetailsResponse> getUserDetails(@NotBlank String username) {
        Mono<UserDetailsResponse> userDetails = userServiceAsyncClient.getUserDetails(username);

        if (userDetails == null) {
            throw new UsersServiceExceptions("Failed to call users service to get user details");
        }

        return userDetails;
    }
}
