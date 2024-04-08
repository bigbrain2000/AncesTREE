package com.weatherbeaconboard.web.model;

import com.weatherbeaconboard.model.UserEntity;
import com.weatherbeaconboard.model.enums.RoleType;
import lombok.Builder;

import java.io.Serializable;

/**
 * Web model for a {@link UserEntity}.
 */
@Builder
public record User(

        Integer id,

        String username,

        String password,

        String firstName,

        String lastName,

        RoleType role,

        String email,

        String address,

        String phoneNumber,

        Boolean locked,

        Boolean enabled,

        Integer version

        )implements Serializable{

        }
