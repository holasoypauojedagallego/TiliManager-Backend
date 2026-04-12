package com.JPAVideoGames.TiliManager.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${spring.jwt.secreto}")
    private String codigoSecreto;

    public String generateToken(Authentication authentication) {

        SecretKey secreto = Keys.hmacShaKeyFor(codigoSecreto.getBytes(StandardCharsets.UTF_8));
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + (1000 * 60 * 60 * 24 * 7)); // 1 semana sin expirar

        String username = authentication.getName();
        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secreto)
                .compact();
    }
}
