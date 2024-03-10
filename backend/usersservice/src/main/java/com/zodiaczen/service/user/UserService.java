package com.zodiaczen.service.user;

import com.zodiaczen.exceptions.*;
import com.zodiaczen.model.ConfirmationTokenEntity;
import com.zodiaczen.model.UserEntity;
import com.zodiaczen.web.model.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Interface used for declaring the methods signature that can be performed with a {@link UserEntity} entity.
 */
public interface UserService extends UserDetailsService {

    /**
     * Retrieve a  {@link User} object based on id.
     *
     * @param id the id of the wanted  {@link User} object
     * @return an object of type {@link User}
     * @throws EntityIsNotExistingException if the searched user does not exist in the database
     */
    User getById(@NotNull Integer id);

    /**
     * Delete a {@link UserEntity} by id alongside with the associated {@link ConfirmationTokenEntity}.
     *
     * @param id the id of the {@link UserEntity} to be deleted
     */
    void deleteById(@NotNull Integer id);

    /**
     * Update a {@link UserEntity} fields.
     *
     * @param id   the id of the user that will be updated
     * @param user the web model mapped as {@link User} that contains the data that will be updated
     * @return the updated {@link UserEntity} mapped as a {@link User}
     * @throws EntityIsNotExistingException if the searched user id does not exist in the database
     */
    User update(@NotNull Integer id, @NotNull @Valid User user);

    boolean isUserEmailAlreadyRegistered(@NotBlank String email);

    void validateAndCheckIfUserEmailExists(@NotNull UserEntity user);

    /**
     * After the email is confirmed, the token will be set as confirmed and the {@link UserEntity} account will be enabled.
     *
     * @param token the unique generated token for a new user
     * @throws ConfirmationTokenExpiredException          if the confirmation token expired
     * @throws ConfirmationTokenAlreadyConfirmedException if the confirmation token already exists in the database
     */
    void confirmToken(@NotBlank String token);

    /**
     * Based on entered data, a new user will be saved in the DB and a token
     * is generated for validating email within the next 30 minutes from registering.
     *
     * @param newUser the user who register in the app
     * @return token the generated confirmation token for user email validation
     * @throws InvalidEmailException        if a user email is invalid
     * @throws EntityAlreadyExistsException if a user with the same email already exists
     * @throws InvalidPhoneNumberException  if the introduced phone number is not valid
     */
    String register(@NotNull User newUser) throws EntityAlreadyExistsException;


    /**
     * Get the username of the logged {@link UserEntity}.
     *
     * @return the logged username if he is logged in the app
     */
    String getLoggedUsername();
}