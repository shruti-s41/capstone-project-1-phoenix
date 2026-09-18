package org.hdfc.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.hdfc.exception.InvalidTokenException;
import org.hdfc.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;


@Service // Added standard Spring stereotype annotation
public class JwtService {


    private String secretKey="IXI7KVeRp92b8Jl0nuZEf0CzWJuIzww03XT7nxpVTY5";

    @Value("${jwt.expiration}")
    private long expirationMs; // Changed from String to long

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(
                secretKey.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateToken(String username) {
        Date now = new Date();
        Date expire = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expire)
                .signWith(getKey())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (InvalidTokenException e) {
            throw new InvalidTokenException("Invalid JWT token");
        }
    }

    public String extractUsername(String token) {
       try {
           Claims claims = Jwts.parser()
                   .verifyWith(getKey())
                   .build()
                   .parseSignedClaims(token)
                   .getPayload();

           return claims.getSubject();
       }catch (ExpiredJwtException e) {
           throw new InvalidTokenException("Expired JWT token");
       }
       catch (JwtException e) {
           throw new InvalidTokenException("Invalid JWT token");
       }
    }
}