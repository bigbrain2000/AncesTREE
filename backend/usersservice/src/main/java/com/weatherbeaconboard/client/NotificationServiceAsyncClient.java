package com.weatherbeaconboard.client;

import com.weatherbeaconboard.config.NotificationServiceClientProperties;
import com.weatherbeaconboard.exceptions.NotificationServiceException;
import com.weatherbeaconboard.web.model.EmailRequest;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.weatherbeaconboard.config.Constants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Component
@Retry(name = NOTIFICATION_SERVICE)
@RateLimiter(name = NOTIFICATION_SERVICE)
public class NotificationServiceAsyncClient {

    private final WebClient webClient;
    private final NotificationServiceClientProperties notificationServiceClientProperties;

    public NotificationServiceAsyncClient(@Qualifier("notificationServiceWebClient") WebClient webClient,
                                          NotificationServiceClientProperties notificationServiceClientProperties) {

        this.webClient = webClient;
        this.notificationServiceClientProperties = notificationServiceClientProperties;
    }

    /**
     * Sends a registration email to the specified recipient.
     * <p>
     * This method initiates an asynchronous request to send an email with the provided
     * subject and body to the specified email address. It logs the success or failure
     * of the email sending operation.
     *
     * @return a {@link Mono<Void>} that completes when the email sending operation is done
     */
    @CircuitBreaker(name = NOTIFICATION_SERVICE_SED_REGISTRATION_EMAIL)
    @Bulkhead(name = NOTIFICATION_SERVICE_SED_REGISTRATION_EMAIL)
    public Mono<Void> sendRegistrationEmail(EmailRequest emailRequest) {
        log.debug("Sending user registration email");

        return webClient.post()
                .uri(notificationServiceClientProperties.getNotificationServiceSendEmailUrl())
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .bodyValue(emailRequest) // Pass the emailRequest object here
                .retrieve()
                .bodyToMono(Void.class)
                .onErrorResume(error -> {
                    log.error("Error occurred during sending the email: {}", error.getMessage());
                    return Mono.error(new NotificationServiceException("Error calling the Notification Service"));
                });
    }
}
