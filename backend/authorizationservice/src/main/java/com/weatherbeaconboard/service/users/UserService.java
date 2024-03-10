package com.weatherbeaconboard.service.users;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    /**
     * Gwt user details under {@link UserDetails} object by calling the users service.
     *
     * @param username the username for which to retrieve the {@link UserDetails} object
     * @return an object of type {@link UserDetails}, that represents the user details
     */
    UserDetails getUserDetails(String username);
}
