package com.zodiaczen.web.model;

import com.zodiaczen.model.UserEntity;
import com.zodiaczen.model.enums.RoleType;
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

        RoleType rol,

        String email,

        String address,

        String phoneNumber,

        Boolean locked,

        Boolean enabled

) implements Serializable {

}
