package com.zodiaczen.service.confirmationtoken;


import com.zodiaczen.exceptions.EntityAlreadyExistsException;
import com.zodiaczen.model.ConfirmationTokenEntity;
import com.zodiaczen.model.UserEntity;
import com.zodiaczen.repository.ConfirmationTokenRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static com.zodiaczen.utils.DateUtils.DATE_TIME_PATTERN;
import static com.zodiaczen.utils.DateUtils.getDateTime;
import static java.lang.String.format;

/**
 * Concrete class that implements the {@link ConfirmationTokenService} interface.
 */
@Slf4j
@Service
@Validated
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private static final OffsetDateTime NOW = getDateTime();

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public ConfirmationTokenServiceImpl(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    /**
     * Get a saved {@link ConfirmationTokenEntity} from the database based on the token.
     *
     * @param token the token to be retrieved
     * @return the persisted searched token
     */
    public ConfirmationTokenEntity getToken(@NotBlank String token) {
        assertTokenExists(token);

        ConfirmationTokenEntity foundConfirmationTokenEntity = confirmationTokenRepository.getByToken(token);
        log.debug("Successfully retrieved {} for token {} ", ConfirmationTokenEntity.class.getSimpleName(), token);

        return foundConfirmationTokenEntity;
    }

    /**
     * Set a token confirmation date.
     *
     * @param token the searched token
     */
    public void setConfirmedAt(@NotBlank String token) {
        log.debug("Token was successfully updated in the database");
        confirmationTokenRepository.updateTokenConfirmationDate(token, getDateTime());
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
        String token = UUID.randomUUID().toString();

        OffsetDateTime defaultDateTime = OffsetDateTime.parse("2000-01-01T20:20:20.200Z",
                DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));

        final ConfirmationTokenEntity confirmationToken = ConfirmationTokenEntity.builder()
                .token(token)
                .tokenCreatedAt(NOW)
                .tokenExpiresAt(NOW.plusMinutes(30))
                .tokenConfirmedAt(defaultDateTime) //if the user did not validate his email, then it will have a default value into the database
                .user(user)
                .build();

        saveConfirmationToken(confirmationToken);

        return token;
    }

    private void saveConfirmationToken(@NotNull @Valid ConfirmationTokenEntity confirmationToken) {
        final Integer confirmationTokenId = confirmationToken.getId();

        assertEntityExists(confirmationTokenId);

        log.debug("Successfully saved a new {} for id {} ", ConfirmationTokenEntity.class.getSimpleName(), confirmationTokenId);
        confirmationTokenRepository.save(confirmationToken);
    }

    private void assertEntityExists(@NotNull Integer id) {
        log.debug("Attempting to save {} for id {} ", ConfirmationTokenEntity.class.getSimpleName(), id);

        if (confirmationTokenRepository.existsById(id)) {
            String errorMessage = format("An object of type %s already exists for the id %s",
                    ConfirmationTokenEntity.class.getSimpleName(), id);

            throw new EntityAlreadyExistsException(errorMessage);
        }
    }

    private void assertTokenExists(@NotBlank String token) {
        log.debug("Attempting to retrieve {} for {} ", ConfirmationTokenEntity.class.getSimpleName(), token);

        if (confirmationTokenRepository.getByToken(token) == null) {
            String errorMessage = format("No confirmation token found for %s", token);

            throw new EntityNotFoundException(errorMessage);
        }
    }
}