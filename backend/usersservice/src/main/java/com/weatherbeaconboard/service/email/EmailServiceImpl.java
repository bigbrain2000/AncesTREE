package com.weatherbeaconboard.service.email;

import com.weatherbeaconboard.client.NotificationServiceAsyncClient;
import com.weatherbeaconboard.web.model.EmailRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final NotificationServiceAsyncClient notificationServiceAsyncClient;

    /**
     * Method used for sending an email. Default, the sender is retrieved from the property files.
     *
     * @param emailRequest the email request under {@link EmailRequest} format.
     * @throws IllegalStateException in case of errors at sending the email
     */
    public void sendRegistrationEmail(EmailRequest emailRequest) {
        notificationServiceAsyncClient.sendRegistrationEmail(emailRequest).subscribe();
    }
}
