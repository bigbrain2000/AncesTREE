package com.zodiaczen.service.email;

import com.zodiaczen.exceptions.InvalidEmailException;
import jakarta.mail.internet.InternetAddress;
import jakarta.validation.constraints.NotBlank;

/**
 * Interface used for declaring the methods signature that can process emails.
 */
public interface EmailService {

    /**
     * Method used for sending an email. Default, the sender is "auctionshunters@gmail.com".
     *
     * @param to      addressee
     * @param subject the email subject
     * @param body-   the email we want to send
     * @throws IllegalStateException in case of errors at sending the email
     */
    void sendEmail(@NotBlank String to, @NotBlank String subject, @NotBlank String body);

    /**
     * Verify is the user email is valid.
     *
     * @param email the email of user
     * @throws InvalidEmailException if a user email is invalid
     */
    void checkIfEmailIsValid(@NotBlank String email) throws InvalidEmailException;

    /**
     * Check if the email parsed as input is valid using {@link InternetAddress}.
     *
     * @param email provided email
     * @return true if the input is a valid email and false otherwise
     */
    boolean isValidEmailAddress(@NotBlank String email);
}
