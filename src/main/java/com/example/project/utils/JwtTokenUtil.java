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

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import static com.example.project.constant.ErrorResponse.EXPIRED_ACCESS_TOKEN;
import static com.example.project.constant.ErrorResponse.TOKEN_ERROR;

@Slf4j
@Component
public class JwtTokenUtil {

    private final static String USER_ID = "userId";
    private final String EXCEPTION = "exception";
    @Value("${jwt.secret-key}")
    private String JWT_SECRET;
    @Value("${jwt.expiration-ms.access-token}")
    private Long ACCESS_TOKEN_EXPIRATION_MS;
    @Value("${jwt.expiration-ms.refresh-token}")
    private Long REFRESH_TOKEN_EXPIRATION_MS;

    public Authentication getAuthentication(String token) {
        Long userId = extractClaims(token).get(USER_ID, Long.class);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                new UserAuthenticationDto(userId), null, null);
        return authentication;
    }

    public Claims extractClaims(String token) {
        try {
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
        claims.put(USER_ID, userId);
        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis())) // 발행시간
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_MS)) // 만료시간
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis())) // 발행시간
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_MS)) // 만료시간
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

        return new TokenResDto(accessToken, refreshToken);
    }

    private Key getKey() {
        byte[] keyBytes = JWT_SECRET.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Jwt 토큰 유효성 검사
    public boolean validateToken(HttpServletRequest request, String token) {
        try {
            Jwts.parser().setSigningKey(getKey()).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            request.setAttribute(EXCEPTION, EXPIRED_ACCESS_TOKEN);
            return false;
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        request.setAttribute(EXCEPTION, TOKEN_ERROR);
        return false;
    }

}
