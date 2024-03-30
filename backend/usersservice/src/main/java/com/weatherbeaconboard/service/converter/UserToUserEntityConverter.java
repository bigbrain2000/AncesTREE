package com.weatherbeaconboard.service.converter;

import com.weatherbeaconboard.model.UserEntity;
import com.weatherbeaconboard.web.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class UserToUserEntityConverter implements Converter<User, UserEntity> {

    @Override
    public UserEntity convert(User source) {
        return UserEntity.builder()
                .id(source.id())
                .username(source.username())
                .password(source.password())
                .firstName(source.firstName())
                .lastName(source.lastName())
                .role(source.role())
                .email(source.email())
                .address(source.address())
                .phoneNumber(source.phoneNumber())
                .enabled(source.enabled())
                .locked(source.locked())
                .version(source.version())
                .build();
    }
}
