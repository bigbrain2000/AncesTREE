package com.zodiaczen.service.converter;

import com.zodiaczen.model.UserEntity;
import com.zodiaczen.web.model.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class UserToUserEntityConverter implements Converter<User, UserEntity> {

    @Override
    public UserEntity convert(@NotNull @Valid User source) {
        return UserEntity.builder()
                .username(source.username())
                .password(source.password())
                .role(source.rol())
                .email(source.address())
                .address(source.address())
                .phoneNumber(source.phoneNumber())
                .build();
    }
}
