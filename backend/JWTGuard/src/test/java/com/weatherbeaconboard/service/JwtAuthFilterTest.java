package com.weatherbeaconboard.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.mockito.Mockito.lenient;


@ExtendWith(MockitoExtension.class)
class JwtAuthFilterTest {

    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private UserDetailsFetcher userServiceFetcher;

    @Mock
    private FilterChain filterChain;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private JwtAuthFilter jwtAuthFilter;

    private String token;

    @BeforeEach
    void setUp() {
        final UserDetails userDetails = new User("testUser", "password", Collections.emptyList());

        token = jwtUtil.generateToken(userDetails);
    }

    @Test
    void doFilterInternal_validToken_authenticationSuccess() throws Exception {
        lenient().when(jwtUtil.validateToken(token)).thenReturn(true);
        lenient().when(jwtUtil.extractUsername(token)).thenReturn("testUser");

        jwtAuthFilter.doFilterInternal(request, response, filterChain);
    }
}
