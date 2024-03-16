package com.weatherbeaconboard.repository;

import com.weatherbeaconboard.model.ConfirmationTokenEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
@Transactional
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationTokenEntity, Integer> {

    /**
     * Retrieved an {@link ConfirmationTokenEntity} from the database based on the token parameter.
     */
    ConfirmationTokenEntity getByToken(String token);


    /**
     * Updates the confirmation date of a specified token in the {@link ConfirmationTokenEntity} table.
     * This method sets the 'tokenConfirmedAt' field to the provided date for the token matching
     * the given 'token' value.
     *
     * @param token                 the token string whose confirmation date needs to be updated.
     * @param tokenConfirmationDate the new confirmation date to be set for the specified token.
     */
    @Modifying
    @Query("UPDATE ConfirmationTokenEntity SET tokenConfirmedAt = :tokenConfirmationDate WHERE token = :token")
    void updateTokenConfirmationDate(@Param("token") String token, @Param("tokenConfirmationDate") OffsetDateTime tokenConfirmationDate);
}