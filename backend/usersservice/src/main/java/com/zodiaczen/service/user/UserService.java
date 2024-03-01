package com.zodiaczen.service.user;


import com.zodiaczen.exceptions.EntityAlreadyExistsException;
import com.zodiaczen.exceptions.InvalidEmailException;
import com.zodiaczen.model.UserEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Interface used for declaring the methods signature that can be performed with a {@link UserEntity} entity.
 */
public interface UserService {


    /**
     * After the email is confirmed, the token will be set as confirmed and the {@link UserEntity} account will be enabled.
     *
     * @param token the unique generated token for a new user
     */
    void confirmToken(@NotBlank String token);

    /**
     * Based on entered data, a new user will be saved in the DB and a token
     * is generated for validating email within the next 30 minutes from registering.
     *
     * @param newUser the user who register in the app
     * @return a String which contains the unique token generated for the registered user
     * @throws InvalidEmailException        if a user email is invalid
     * @throws EntityAlreadyExistsException if a user with the same email already exists
     */
    String saveUserAndSendConfirmationToken(@NotNull UserEntity newUser) throws EntityAlreadyExistsException;
}