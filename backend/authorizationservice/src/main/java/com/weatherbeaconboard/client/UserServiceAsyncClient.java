package com.weatherbeaconboard.client;

import com.weatherbeaconboard.config.UserServiceClientProperties;
import com.weatherbeaconboard.web.model.UserDetailsResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.function.Function;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Service
public class UserServiceAsyncClient {

    private final WebClient webClient;
    private final UserServiceClientProperties userServiceClientProperties;

    public UserServiceAsyncClient(@Qualifier("usersServiceWebClient") WebClient webClient,
                                  UserServiceClientProperties userServiceClientProperties) {

        this.webClient = webClient;
        this.userServiceClientProperties = userServiceClientProperties;
    }

    /**
     * Get the user details under {@link UserDetails} object.
     *
     * @param username the username for which to retrieve the {@link UserDetails} object.
     * @return A {@link Mono} emitting the {@link UserDetails} object, or {@link Mono#empty()} if not found.
     */
    public Mono<UserDetailsResponse> getUserDetails(@NotBlank String username) {
        log.debug("Retrieving UserDetails from users service");

        final Function<UriBuilder, URI> uriBuilderURIFunction = uriBuilder -> uriBuilder.path(userServiceClientProperties.getUserServiceUserDetailsUrl())
                .queryParam("username", username)
                .build();

        return webClient.get()
                .uri(uriBuilderURIFunction)
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToMono(UserDetailsResponse.class)
                .switchIfEmpty(Mono.empty());
    }
}
