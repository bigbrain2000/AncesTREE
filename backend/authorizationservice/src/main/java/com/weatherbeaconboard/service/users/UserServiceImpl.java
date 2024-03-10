package com.weatherbeaconboard.service.users;

import com.weatherbeaconboard.client.UserServiceAsyncClient;
import com.weatherbeaconboard.exceptions.UsersServiceExceptions;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserServiceAsyncClient userServiceAsyncClient;

    public UserDetails getUserDetails(String username) {
        UserDetails userDetails = userServiceAsyncClient.getUserDetails(username).block();

        if (userDetails == null) {
            throw new UsersServiceExceptions("Failed to call users service to get user details");
        }

        return userDetails;
    }
}
