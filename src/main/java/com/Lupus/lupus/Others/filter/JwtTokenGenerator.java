package com.Lupus.lupus.Others.filter;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenGenerator {
    private static final String SecretToken = "mySecretKey12345678901234567890asdawdvwoiurehbuoiawnbov";

    private final Key key = Keys.hmacShaKeyFor(SecretToken.getBytes(StandardCharsets.UTF_8));

    public String generateToken(String username, Authentication authentication) {
        String roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        System.out.println("Role Uzytkownika : " + roles);
        return Jwts.builder()
                .setSubject(username)
                .claim("roles",roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 24000 * 60 * 60)) // Token ważny przez godzinę
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String validateToken(String token){
        try{
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return claims.getBody().getSubject();
        } catch (ExpiredJwtException e) {
            // Token wygasł
            throw new RuntimeException("Token wygasł: " + e.getMessage());
        } catch (MalformedJwtException e) {
            // Token jest nieprawidłowy
            throw new RuntimeException("Token jest nieprawidłowy: " + e.getMessage());
        } catch (SignatureException e) {
            // Niepoprawny podpis tokena
            throw new RuntimeException("Niepoprawny podpis tokena: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Token jest pusty lub null
            throw new RuntimeException("Token jest pusty: " + e.getMessage());
        }
    }

    public Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}
