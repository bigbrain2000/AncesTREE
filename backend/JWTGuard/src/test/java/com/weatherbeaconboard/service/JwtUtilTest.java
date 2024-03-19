package com.weatherbeaconboard.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class JwtUtilTest {

    private final JwtUtil uut = new JwtUtil();

    private String token;

    @BeforeEach
    void setUp() {
        final UserDetails userDetails = new User("testUser", "password", Collections.emptyList());
        token = uut.generateToken(userDetails);
    }

    @Test
    void testExtractUsername() {
        final String username = uut.extractUsername(token);

        assertEquals("testUser", username);
    }

    @Test
    void testValidateToken() {
        assertTrue(uut.validateToken(token));
    }
}
