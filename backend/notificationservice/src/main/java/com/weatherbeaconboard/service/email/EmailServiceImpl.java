package com.weatherbeaconboard.service.email;

import com.weatherbeaconboard.config.EmailProperties;
import com.weatherbeaconboard.exceptions.EmailSendingException;
import com.weatherbeaconboard.web.model.EmailRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
     * Method used for sending an email. Default, the sender is retrieved from the property files.
     *
     * @param emailRequest the email request under {@link EmailRequest} format.
     * @throws IllegalStateException in case of errors at sending the email
     */
    @Async
    @Override
    public void sendEmail(@NotNull @Valid EmailRequest emailRequest) {
        final String to = emailRequest.to();
        final String subject = emailRequest.subject();
        final String body = emailRequest.body();

        try {
            final MimeMessage mimeMessage = mailSender.createMimeMessage();
            final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            helper.setFrom(emailProperties.getApplicationEmail());

            mailSender.send(mimeMessage);
            log.info("Email sent to {} with subject: {}", to, subject);

        } catch (MessagingException e) {
            log.error("Failed to send email to {} with subject: {}", to, subject, e);
            throw new EmailSendingException("Failed to send email to " + to, e);
        }
    }
}
