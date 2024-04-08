package com.weatherbeaconboard.web.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public record UserDetailsResponse(

@NotNull
        List<AuthorityDetail> authorities,

@NotNull
        String password,

@NotNull
        String username,

@NotNull
        boolean isAccountNonExpired,

@NotNull
        boolean isAccountNonLocked,

@NotNull
        boolean isCredentialsNonExpired,

@NotNull
        boolean isEnabled

                )implements Serializable{
                }
