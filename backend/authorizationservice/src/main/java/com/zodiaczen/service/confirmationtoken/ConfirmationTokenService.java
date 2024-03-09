package com.zodiaczen.service.confirmationtoken;

import com.zodiaczen.exceptions.EntityAlreadyExistsException;
import com.zodiaczen.model.ConfirmationTokenEntity;
import com.zodiaczen.model.UserEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public interface ConfirmationTokenService {

    /**
     * Get a saved {@link ConfirmationTokenEntity} from the database based on the token.
     *
     * @param token the token to be retrieved
     * @return the persisted searched token
     */
    ConfirmationTokenEntity getToken(@NotBlank String token);

    /**
     * Set a token confirmation date.
     *
     * @param token the searched token
     */
    void setConfirmedAt(@NotBlank String token);

    /**
     * Deletes a {@link ConfirmationTokenEntity} by id.
     *
     * @param id the id of the {@link ConfirmationTokenEntity} to be deleted
     */
    void deleteById(@NotNull Integer id);

    /**
     * Creates a {@link ConfirmationTokenEntity} and save it in the database for the user who generated it.
     *
     * @param user the {@link UserEntity} that generated the token
     * @return the token as a string representation
     * @throws EntityAlreadyExistsException if there`s already a {@link ConfirmationTokenEntity} saved in the database
     *                                      with the same id
     */
    String saveAndReturnAConfirmationToken(@NotNull @Valid UserEntity user);
}
