package com.healthcare.patientmanagementapi.security;

import com.healthcare.patientmanagementapi.model.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "your-256-bit-secret-your-256-bit-secret"; // 🔐 Must be 256-bit (32+ chars)
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours
        // we can also use refresh tokens so o each refresh you login again
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // Generate token
    public String generateToken(String email, Role role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role.name())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    //  Extract email
    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    //  Extract role
    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

    //  Validate token
    public boolean validateToken(String token) {
        try {
            extractClaims(token); // will throw if invalid
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Private helper
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
