package com.zodiaczen.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.time.OffsetDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static com.zodiaczen.utils.DateUtils.DATE_TIME_PATTERN;

@Entity
@Table(name = "confirmation_token")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class ConfirmationTokenEntity {
    //todo: add class documentation

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