package com.weatherbeaconboard.web.controller;

import com.weatherbeaconboard.service.JwtUtil;
import com.weatherbeaconboard.service.users.UserService;
import com.weatherbeaconboard.web.model.AuthenticationUserResponse;
import com.weatherbeaconboard.web.model.JWTResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping(value = "/v1/auth/")
@AllArgsConstructor
public class AuthController {

    private UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/refresh/{username}")
    public Mono<ResponseEntity<JWTResponse>> refreshToken(@PathVariable @NotBlank String username) {
        log.info("Attempting to refresh token for user {}", username);

        return userService.getUserDetails(username)
                .map(userDetails -> {
                    String newAccessToken = jwtUtil.generateToken(userDetails);
                    log.info("Access token successfully refreshed for user: {}", username);
                    JWTResponse jwtResponse = new JWTResponse(newAccessToken);
                    return ResponseEntity.ok(jwtResponse);
                });
    }

    @PostMapping("/login/{username}")
    public Mono<ResponseEntity<AuthenticationUserResponse>> loginUser(@PathVariable @NotBlank String username) {
        log.info("Attempting to refresh token for user {}", username);

        return userService.getUserDetails(username)
                .map(userDetails -> {
                    final String jwtToken = jwtUtil.generateToken(userDetails);

                    log.info("Login successful for user: {}", userDetails.username());
                    AuthenticationUserResponse response = new AuthenticationUserResponse(jwtToken,
                            userDetails.username(), "Bearer");

                    return ResponseEntity.ok(response);
                });
    }
}