package com.zodiaczen.web.model;

import com.zodiaczen.model.UserEntity;
import com.zodiaczen.model.enums.RoleType;
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
