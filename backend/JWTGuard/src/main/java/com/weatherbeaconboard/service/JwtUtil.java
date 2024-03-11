package com.weatherbeaconboard.service;

import com.weatherbeaconboard.web.model.UserDetailsResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.weatherbeaconboard.config.JwtProperties.JWT_EXPIRATION_MS;
import static com.weatherbeaconboard.config.JwtProperties.JWT_SECRET;

@Component
@AllArgsConstructor
public class JwtUtil {

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(@NotNull @Valid UserDetailsResponse userDetails) {
        return createToken(userDetails.username(), JWT_EXPIRATION_MS, false);
    }

    public String generateToken(@NotNull UserDetails userDetails) {
        return createToken(userDetails.getUsername(), JWT_EXPIRATION_MS, false);
    }

    private String createToken(String username, long expirationTime, boolean isRefreshToken) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        if (isRefreshToken) {
            claims.put("isRefresh", true);
        }

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(Keys.hmacShaKeyFor(JWT_SECRET.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = extractExpiration(token);
        return expiration.before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
