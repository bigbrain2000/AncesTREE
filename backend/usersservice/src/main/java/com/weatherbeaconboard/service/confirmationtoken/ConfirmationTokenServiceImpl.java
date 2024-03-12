package com.weatherbeaconboard.service.confirmationtoken;

import com.weatherbeaconboard.exceptions.EntityAlreadyExistsException;
import com.weatherbeaconboard.model.ConfirmationTokenEntity;
import com.weatherbeaconboard.model.UserEntity;
import com.weatherbeaconboard.repository.ConfirmationTokenRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static com.weatherbeaconboard.utils.DateUtils.DATE_TIME_PATTERN;
import static com.weatherbeaconboard.utils.DateUtils.getDateTime;
import static java.lang.String.format;

/**
 * Concrete class that implements the {@link ConfirmationTokenService} interface.
 */
@Slf4j
@Service
@Validated
@AllArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private static final OffsetDateTime NOW = getDateTime();

    private final ConfirmationTokenRepository confirmationTokenRepository;

    /**
     * Get a saved {@link ConfirmationTokenEntity} from the database based on the token.
     *
     * @param token the token to be retrieved
     * @return the persisted searched token
     */
    public ConfirmationTokenEntity getToken(@NotBlank String token) {
        log.debug("Attempting to retrieve {} for {} ", ConfirmationTokenEntity.class.getSimpleName(), token);

        final ConfirmationTokenEntity confirmationToken = confirmationTokenRepository.getByToken(token);

        if (confirmationToken == null) {
            final String errorMessage = format("No confirmation token found for %s", token);

            throw new EntityNotFoundException(errorMessage);
        }

        log.debug("Successfully retrieved {} for token {} ", ConfirmationTokenEntity.class.getSimpleName(), token);

        return confirmationToken;
    }

    /**
     * Sets a token confirmation date.
     *
     * @param token the searched token
     */
    public void setConfirmedAt(@NotBlank String token) {
        log.debug("Attempting to update a confirmation by the token {}", token);
        confirmationTokenRepository.updateTokenConfirmationDate(token, getDateTime());
        log.debug("Token was successfully updated in the database");
    }

    /**
     * Deletes a {@link ConfirmationTokenEntity} by id.
     *
     * @param id the id of the {@link ConfirmationTokenEntity} to be deleted
     */
    public void deleteById(@NotNull Integer id) {
        log.debug("Attempting to delete a confirmation by the id {}", id);
        confirmationTokenRepository.deleteById(id);
        log.debug("Successfully deleted the confirmation token with id {}", id);

    }

    /**
     * Creates a {@link ConfirmationTokenEntity} and save it in the database for the user who generated it.
     *
     * @param user the {@link UserEntity} that generated the token
     * @return the token as a string representation
     * @throws EntityAlreadyExistsException if there`s already a {@link ConfirmationTokenEntity} saved in the database
     *                                      with the same id
     */
    public String saveAndReturnAConfirmationToken(@NotNull @Valid UserEntity user) {
        log.debug("Attempting to save a confirmation token for email validation.");
        final String token = UUID.randomUUID().toString();

        final OffsetDateTime defaultDateTime = OffsetDateTime.parse("2000-01-01T20:20:20.200Z",
                DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));

        final ConfirmationTokenEntity confirmationTokenEntity = ConfirmationTokenEntity.builder()
                .token(token)
                .tokenCreatedAt(NOW)
                .tokenExpiresAt(NOW.plusMinutes(30))
                .tokenConfirmedAt(defaultDateTime) //if the user did not validate his email, then it will have a default value into the database
                .user(user)
                .build();

        final ConfirmationTokenEntity savedConfirmationTokenEntity = confirmationTokenRepository.save(confirmationTokenEntity);
        log.debug("Successfully saved a new confirmation token {}", savedConfirmationTokenEntity);

        return token;
    }
}