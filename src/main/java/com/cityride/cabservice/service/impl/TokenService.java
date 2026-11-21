package com.cityride.cabservice.service.impl;

import com.cityride.cabservice.dto.AccessTokenDto;
import com.cityride.cabservice.entity.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtEncoder jwtEncoder;

    @Value("${app.jwt.expiration-minutes:60}")
    private long expirationMinutes;

    public long getExpiresInSeconds() {
        return Duration.ofMinutes(expirationMinutes).toSeconds();
    }

    public String generateToken(AccessTokenDto user) {
        Instant now = Instant.now();

        String role = user.getRole().toString().replaceFirst("^ROLE_", "");

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("cityride-cabservice")
                .issuedAt(now)
                .expiresAt(now.plus(Duration.ofMinutes(expirationMinutes)))
                .subject(user.getEmail())
                .claim("uid", user.getUserId())
                .claim("roles", List.of(role))
                .claim("expiresInSeconds", user.getExpiresInSeconds())
                .claim("tokenType", user.getTokenType())
                .claim("email", user.getEmail())
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }
}