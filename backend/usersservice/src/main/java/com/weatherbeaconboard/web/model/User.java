package com.weatherbeaconboard.web.model;

import com.weatherbeaconboard.model.UserEntity;
import com.weatherbeaconboard.model.enums.RoleType;
import lombok.Builder;

import java.io.Serializable;
import java.time.OffsetDateTime;

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

        OffsetDateTime birthday,

        String email,

        String address,

        String phoneNumber

) implements Serializable {

}
