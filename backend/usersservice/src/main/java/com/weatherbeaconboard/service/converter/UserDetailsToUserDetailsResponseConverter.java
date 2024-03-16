package com.weatherbeaconboard.service.converter;

import com.weatherbeaconboard.web.model.AuthorityDetail;
import com.weatherbeaconboard.web.model.UserDetailsResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class UserDetailsToUserDetailsResponseConverter implements Converter<UserDetails, UserDetailsResponse> {

    @Override
    public UserDetailsResponse convert(UserDetails source) {
        final List<AuthorityDetail> authorityDetailsList = source.getAuthorities().stream()
                .map(this::mapAuthority)
                .toList();

        return UserDetailsResponse.builder()
                .authorities(authorityDetailsList)
                .username(source.getUsername())
                .password(source.getPassword())
                .isAccountNonExpired(source.isAccountNonExpired())
                .isAccountNonLocked(source.isAccountNonLocked())
                .isCredentialsNonExpired(source.isCredentialsNonExpired())
                .isEnabled(source.isEnabled())
                .build();
    }

    private AuthorityDetail mapAuthority(GrantedAuthority authority) {
        final String authorityName = authority.getAuthority();

        return new AuthorityDetail(authorityName);
    }
}