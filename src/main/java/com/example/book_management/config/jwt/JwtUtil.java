package com.example.book_management.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    // SECRET KEY (keep safe)
    private static final String SECRET = "my-secret-key-my-secret-key-my-secret-key";

    // Token validity (1 hour)
    private static final long EXPIRATION_TIME = 60 * 60 * 1000;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // Generate JWT
    public String generateToken(String username, List<String> roles) {
        return Jwts.builder()
                .setSubject(username)                     // username
                .claim("roles", roles)                    // roles
                .setIssuedAt(new Date())                  // now
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract username
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // Extract roles
    public List<String> extractRoles(String token) {
        return getClaims(token).get("roles", List.class);
    }

    // Validate token
    public boolean isTokenValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
