package com.weatherbeaconboard.service.converter;

import com.weatherbeaconboard.web.model.UserDetailsResponse;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDetailsToUserDetailsResponseConverterTest {

    private final UserDetailsToUserDetailsResponseConverter uut = new UserDetailsToUserDetailsResponseConverter();

    @Test
    void convert_validUserDetails_returnUserDetailsResponse() {
        final String username = "username";
        final String password = "password";
        final boolean isAccountNonExpired = true;
        final boolean isAccountNonLocked = true;
        final boolean isCredentialsNonExpired = true;
        final boolean isEnabled = true;

        final UserDetailsResponse expectedUserDetailsResponse = UserDetailsResponse.builder()
                .authorities(emptyList())
                .username(username)
                .password(password)
                .isAccountNonExpired(isAccountNonExpired)
                .isAccountNonLocked(isAccountNonLocked)
                .isCredentialsNonExpired(isCredentialsNonExpired)
                .isEnabled(isEnabled)
                .build();

        final UserDetails expectedUserDetails = User.builder()
                .authorities(emptyList())
                .username(username)
                .password(password)
                .accountExpired(!isAccountNonExpired)
                .accountLocked(!isAccountNonLocked)
                .credentialsExpired(!isCredentialsNonExpired)
                .disabled(!isEnabled)
                .build();

        final UserDetailsResponse actualUserDetailsResponse = uut.convert(expectedUserDetails);

        assertEquals(expectedUserDetailsResponse, actualUserDetailsResponse);
    }
}