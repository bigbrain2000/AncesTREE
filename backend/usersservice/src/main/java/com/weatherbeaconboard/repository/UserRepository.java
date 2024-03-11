package com.weatherbeaconboard.repository;

import com.weatherbeaconboard.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {


    /**
     * This query that retrieves a {@link UserEntity} entity from the database where the email field is equal to the value of the "username" parameter.
     */
    @Query("SELECT u from UserEntity u Where u.username = :username")
    Optional<UserEntity> findByUsername(@Param("username") String username);

    /**
     * This query that retrieves a {@link UserEntity} entity from the database where the email field is equal to the value of the "email" parameter.
     */
    @Query("SELECT u from UserEntity u Where u.email = :email")
    Optional<UserEntity> findByEmail(@Param("email") String email);

    /**
     * This query updates the "enabled" field of a {@link UserEntity}  in the database to "true" where the email of
     * the user matches the email specified as the first parameter in the query.
     */
    @Modifying
    @Query("UPDATE UserEntity a SET a.enabled = TRUE WHERE a.email = ?1")
    void enableUser(String email);

    /**
     * This query updates the "locked" field of a {@link UserEntity} in the database to "true" where the email of
     * the user matches the email specified as the first parameter in the query.
     */
    @Modifying
    @Query("UPDATE UserEntity a SET a.locked = FALSE WHERE a.email = ?1")
    void unlockUser(String email);
}
