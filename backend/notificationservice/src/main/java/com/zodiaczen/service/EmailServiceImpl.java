package com.zodiaczen.service;

import com.zodiaczen.config.EmailProperties;
import com.zodiaczen.exceptions.EmailSendingException;
import com.zodiaczen.exceptions.InvalidEmailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@Validated
@EnableAsync
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final EmailProperties emailProperties;

    /**
     * Sends an email to the specified recipient.
     *
     * @param to      the recipient's email address
     * @param subject the subject of the email
     * @param body    the content of the email
     */
    @Async
    @Override
    public void sendEmail(@NotBlank String to, @NotBlank String subject, @NotBlank String body) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());
            helper.setText(body, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom(emailProperties.getApplicationEmail());

            mailSender.send(mimeMessage);
            log.info("Email sent to {} with subject: {}", to, subject);

        } catch (MessagingException e) {
            log.error("Failed to send email to {} with subject: {}", to, subject, e);
            throw new EmailSendingException("Failed to send email to " + to, e);
        }
    }

    /**
     * Verify is the user email is valid.
     *
     * @param email the email of user
     * @throws InvalidEmailException if a user email is invalid
     */
    public void checkIfEmailIsValid(@NotBlank String email) throws InvalidEmailException {
        if (!isValidEmailAddress(email) || email.isEmpty()) {
            log.error("User email {} is not valid", email);
            throw new InvalidEmailException(email);
        }
    }

    /**
     * Check if the email parsed as input is valid using {@link InternetAddress}.
     *
     * @param email provided email
     * @return true if the input is a valid email and false otherwise
     */
    private boolean isValidEmailAddress(@NotBlank String email) {

        boolean result = true;
        try {
            InternetAddress emailAddress = new InternetAddress(email);
            emailAddress.validate();
        } catch (AddressException ex) {
            result = false;
        }

        return result;
    }
}
