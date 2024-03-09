package com.zodiaczen.service.converter;

import com.zodiaczen.model.UserEntity;
import com.zodiaczen.web.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class UserEntityToUserConverter implements Converter<UserEntity, User> {

    @Override
    public User convert(UserEntity source) {
        return User.builder()
                .username(source.getUsername())
                .password(source.getPassword())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .role(source.getRole())
                .birthday(source.getBirthday())
                .email(source.getEmail())
                .address(source.getAddress())
                .phoneNumber(source.getPhoneNumber())
                .build();
    }
}