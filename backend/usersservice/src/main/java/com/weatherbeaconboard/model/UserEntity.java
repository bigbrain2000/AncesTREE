package com.weatherbeaconboard.model;

import com.weatherbeaconboard.model.enums.RoleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.SEQUENCE;

/**
 * Database model for a user.
 * </br>
 * <p>
 * Components:
 * <ul>
 *     <li>id- the table primary key</li>
 *     <li>username- the user name which must be unique</li>
 *     <li>password- the user password</li>
 *     <li>role- the user role, values mapped by {@link RoleType}</li>
 *     <li>email- the user email which must be unique</li>
 *     <li>address- the user address</li>
 *     <li>phoneNumber- the user phone number</li>
 *     <li>locked- property set default to {@code TRUE} if a user did not confirmed his email</li>
 *     <li>enabled- property set default to {@code FALSE} if a user did not confirmed his email</li>
 *     <li>version- default is set to 0 and represents the number of the Optimistic locking</li>
 * </ul>
 */
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "email", columnNames = "email"),
        @UniqueConstraint(name = "username", columnNames = "username")
})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "user_sequence")
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Integer id;

    @Column(name = "username", nullable = false)
    private String username;

    @Size(min = 8, message = "Password must be at least 8 characters")
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Enumerated(STRING)
    private RoleType role;

    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Builder.Default
    @Column(name = "locked", nullable = false)
    private Boolean locked = false;

    @Builder.Default
    @Column(name = "enabled", nullable = false)
    private Boolean enabled = false;

    @Version
    private Integer version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity user = (UserEntity) o;

        return new EqualsBuilder()
                .append(id, user.id)
                .append(username, user.username)
                .append(password, user.password)
                .append(role, user.role)
                .append(email, user.email)
                .append(address, user.address)
                .append(phoneNumber, user.phoneNumber)
                .append(version, user.version)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}