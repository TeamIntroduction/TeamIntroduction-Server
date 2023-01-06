package com.example.project.utils;

import com.example.project.dto.token.TokenResDto;
import com.example.project.dto.user.UserAuthenticationDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenUtil {

    @Value("${jwt.secret-key}")
    private String JWT_SECRET;

    @Value("${jwt.expiration-ms.access-token}")
    private Long JWT_EXPIRATION_MS;

    public Authentication getAuthentication(String token) {
        Long userId = extractClaims(token).get("userId", Long.class);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                new UserAuthenticationDto(userId), null, null);
        return authentication;
    }

    public Claims extractClaims(String token) {
        try {
            System.out.println("here?");
            return Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public TokenResDto generateToken(Long userId) {

        Claims claims = Jwts.claims();
        claims.put("userId", userId);
        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis())) // 발행시간
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_MS)) // 만료시간
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

        return new TokenResDto(accessToken);

//        String authorities = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(","));

//        long now = (new Date()).getTime();
//        // Access Token 생성
//        Date accessTokenExpiresIn = new Date(now + 86400000);
//        String accessToken = Jwts.builder()
//                .setSubject(authentication.getName())
//                .claim("auth", authorities)
//                .setExpiration(accessTokenExpiresIn)
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();
//
//        // Refresh Token 생성
//        String refreshToken = Jwts.builder()
//                .setExpiration(new Date(now + 86400000))
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();
//
//        return TokenInfo.builder()
//                .grantType("Bearer")
//                .accessToken(accessToken)
//                .refreshToken(refreshToken)
//                .build();
    }

    private Key getKey() {
        byte[] keyBytes = JWT_SECRET.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Jwt 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(getKey()).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

}
