package com.zodiaczen.web.model;

import com.zodiaczen.model.enums.RolType;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record User(

        Integer id,

        String username,

        String password,

        RolType rol,

        String email,

        String address,

        String phoneNumber,

        Boolean locked,

        Boolean enabled

) implements Serializable {

}
