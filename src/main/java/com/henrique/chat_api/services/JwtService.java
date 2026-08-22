package com.henrique.chat_api.services;

import com.henrique.chat_api.dtos.TokensDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${access.expiration.hours}")
    private int accessTokenExpiration;

    @Value("${refresh.expiration.hours}")
    private int refreshTokenExpiration;

    public TokensDTO generateTokens(UserDetails userDetails) {
        return new TokensDTO(
                generateToken(userDetails, accessTokenExpiration),
                generateToken(userDetails, refreshTokenExpiration)
        );
    }

    public boolean isValid(String token, UserDetails userDetails) {
        Claims claims = extractAllClaims(token);
        String username = claims.getSubject();
        return (username.equals(userDetails.getUsername()) && !isExpired(token));
    }

    public boolean isExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public Claims extractAllClaims(String token) {
        System.out.println(token);
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private String generateToken(UserDetails userDetails, int expirationHours) {
        int expirationMs = expirationHours * 1000 * 60 * 60;
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationMs);
        Set<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(now)
                .expiration(expiration)
                .issuer("chat-api")
                .signWith(getKey())
                .claim("roles", roles)
                .compact();
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
