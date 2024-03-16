package com.weatherbeaconboard.service.email;

import com.weatherbeaconboard.web.model.EmailRequest;

public interface EmailService {

    /**
     * Method used for sending an email. Default, the sender is retrieved from the property files.
     *
     * @param emailRequest the email request under {@link EmailRequest} format.
     * @throws IllegalStateException in case of errors at sending the email
     */
    void sendRegistrationEmail(EmailRequest emailRequest);
}
