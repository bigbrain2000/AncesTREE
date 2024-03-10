package com.weatherbeaconboard.web.controller;

import com.weatherbeaconboard.service.JwtUtil;
import com.weatherbeaconboard.service.SecurityConfig;
import com.weatherbeaconboard.service.users.UserService;
import com.weatherbeaconboard.web.model.AuthenticationUserRequest;
import com.weatherbeaconboard.web.model.AuthenticationUserResponse;
import com.weatherbeaconboard.web.model.JWTResponse;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/v1/auth/")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private UserService userService;
    private final JwtUtil jwtUtil;
    private final SecurityConfig securityConfig;

    @PostMapping("/refresh")
    public ResponseEntity<JWTResponse> refreshToken() {
        log.info("Attempting to refresh token");

        final String username = securityConfig.getLoggedUsername();
        final UserDetails userDetails = userService.getUserDetails(username);
        final String newAccessToken = jwtUtil.generateToken(userDetails);
        log.info("Access token successfully refreshed for user: {}", username);

        JWTResponse jwtResponse = new JWTResponse(newAccessToken);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationUserResponse> loginUser(@RequestBody @NotNull AuthenticationUserRequest request) {
        log.info("Attempting login for user: {}", request.username());

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String jwtToken = jwtUtil.generateToken((UserDetails) authentication.getPrincipal());
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.info("Login successful for user: {}", userDetails.getUsername());

        final AuthenticationUserResponse response = new AuthenticationUserResponse(jwtToken,
                userDetails.getUsername(), "Bearer");
        return ResponseEntity.ok(response);
    }
}
