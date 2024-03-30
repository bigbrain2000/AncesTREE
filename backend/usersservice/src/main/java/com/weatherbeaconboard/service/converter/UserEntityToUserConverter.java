package com.weatherbeaconboard.service.converter;

import com.weatherbeaconboard.model.UserEntity;
import com.weatherbeaconboard.web.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class UserEntityToUserConverter implements Converter<UserEntity, User> {

    @Override
    public User convert(UserEntity source) {
        return User.builder()
                .id(source.getId())
                .username(source.getUsername())
                .password(source.getPassword())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .role(source.getRole())
                .email(source.getEmail())
                .address(source.getAddress())
                .phoneNumber(source.getPhoneNumber())
                .enabled(source.getEnabled())
                .locked(source.getLocked())
                .version(source.getVersion())
                .build();
    }
}