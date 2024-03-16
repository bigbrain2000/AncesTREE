package com.weatherbeaconboard.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.time.OffsetDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static com.weatherbeaconboard.utils.DateUtils.DATE_TIME_PATTERN;

/**
 * Database model for a confirmation token.
 * </br>
 * <p>
 * Components:
 * <ul>
 *     <li>id- the table primary key</li>
 *     <li>token- a unique token generated for each {@link UserEntity} for confirming his email</li>
 *     <li>tokenCreatedAt- the timestamp when the {code token} was created</li>
 *     <li>tokenExpiresAt- the timestamp when the {code token} expires</li>
 *     <li>tokenConfirmedAt- the timestamp when the {code token} was confirmed</li>
 * </ul>
 */
@Entity
@Table(name = "confirmation_token")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class ConfirmationTokenEntity {

    @Id
    private Integer id;

    @Column(name = "token", nullable = false)
    private String token;

    @JsonFormat(shape = STRING, pattern = DATE_TIME_PATTERN)
    @Column(name = "creation_date", nullable = false)
    private OffsetDateTime tokenCreatedAt;

    @JsonFormat(shape = STRING, pattern = DATE_TIME_PATTERN)
    @Column(name = "expiration_date", nullable = false)
    private OffsetDateTime tokenExpiresAt;

    @JsonFormat(shape = STRING, pattern = DATE_TIME_PATTERN)
    @Column(name = "confirmation_date")
    private OffsetDateTime tokenConfirmedAt;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private UserEntity user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConfirmationTokenEntity confirmationToken = (ConfirmationTokenEntity) o;

        return new EqualsBuilder()
                .append(id, confirmationToken.id)
                .append(token, confirmationToken.token)
                .append(tokenCreatedAt, confirmationToken.tokenCreatedAt)
                .append(tokenExpiresAt, confirmationToken.tokenExpiresAt)
                .append(tokenConfirmedAt, confirmationToken.tokenConfirmedAt)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}