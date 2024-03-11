package com.weatherbeaconboard.service.email;

import com.weatherbeaconboard.web.model.EmailRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Interface used for declaring the methods signature that can process emails.
 */
public interface EmailService {

    /**
     * Method used for sending an email. Default, the sender is retrieved from the property files.
     *
     * @param emailRequest the email request under {@link EmailRequest} format.
     * @throws IllegalStateException in case of errors at sending the email
     */
    void sendEmail(@NotNull @Valid EmailRequest emailRequest);
}
