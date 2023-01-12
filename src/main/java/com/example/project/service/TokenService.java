package com.example.project.service;

import com.example.project.dto.token.ReissueTokenReqDto;
import com.example.project.dto.token.TokenResDto;
import com.example.project.exception.err40x.UnauthorizedException;
import com.example.project.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

import static com.example.project.constant.ErrorResponse.TOKEN_ERROR;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TokenService {

    private final JwtTokenUtil jwtTokenUtil;

    private final String REFRESH_TOKEN = "REFRESH_TOKEN";
    private final String USER_ID = "userId";
    private Long userId;
    public TokenResDto reissueToken(HttpSession session, ReissueTokenReqDto request) {

        String refreshToken = request.getRefreshToken();

        validateToken(session, refreshToken);

        TokenResDto tokenResDto = jwtTokenUtil.generateToken(userId);
        session.setAttribute(REFRESH_TOKEN, tokenResDto.getRefreshToken());
        return tokenResDto;
    }

    private void validateToken(HttpSession session, String refreshToken) {
        if (jwtTokenUtil.validateToken(refreshToken)) {
            userId = jwtTokenUtil.extractClaims(refreshToken).get(USER_ID, Long.class);
        }
        checkUserId(session);
    }

    private void checkUserId(HttpSession session) {
        String previousRefreshToken = (String) session.getAttribute(REFRESH_TOKEN);
        Long previousRefreshTokenUserId = jwtTokenUtil.extractClaims(previousRefreshToken).get(USER_ID, Long.class);
        if (userId != previousRefreshTokenUserId) {
            throw new UnauthorizedException(TOKEN_ERROR);
        }
    }

}
