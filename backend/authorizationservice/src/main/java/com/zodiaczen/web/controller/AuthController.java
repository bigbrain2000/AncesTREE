package com.zodiaczen.web.controller;

import com.zodiaczen.exceptions.InvalidEmailException;
import com.zodiaczen.security.JwtUtil;
import com.zodiaczen.service.user.UserService;
import com.zodiaczen.web.model.AuthenticationUserRequest;
import com.zodiaczen.web.model.AuthenticationUserResponse;
import com.zodiaczen.web.model.JWTResponse;
import com.zodiaczen.web.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequestMapping(value = "/v1/auth/")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @NotNull User user) throws InvalidEmailException {
        userService.register(user);
        return new ResponseEntity<>(OK);
    }

    @GetMapping("/confirm")
    public ResponseEntity<Void> confirm(@RequestParam("token") @NotBlank String token) {
        userService.confirmToken(token);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JWTResponse> refreshToken() {
        log.info("Attempting to refresh token");

        final String username = userService.getLoggedUsername();
        final UserDetails userDetails = userService.loadUserByUsername(username);
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
