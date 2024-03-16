package com.weatherbeaconboard.web.controller;

import com.weatherbeaconboard.service.JwtUtil;
import com.weatherbeaconboard.service.security.SecurityConfig;
import com.weatherbeaconboard.service.users.UserService;
import com.weatherbeaconboard.web.model.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/v1/auth/")
@AllArgsConstructor
public class AuthController {

    private UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/refresh/{username}")
    public Mono<ResponseEntity<JWTResponse>> refreshToken(@PathVariable String username) {
        log.info("Attempting to refresh token for user {}", username);

        return userService.getUserDetails(username)
                .map(userDetails -> {
                    String newAccessToken = jwtUtil.generateToken(userDetails);
                    log.info("Access token successfully refreshed for user: {}", username);
                    JWTResponse jwtResponse = new JWTResponse(newAccessToken);
                    return ResponseEntity.ok(jwtResponse);
                });
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthenticationUserResponse>> loginUser(@RequestBody @NotNull AuthenticationUserRequest request) {
        final String username = request.username();
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