package com.example.project.service;

import com.example.project.dto.token.ReissueTokenReqDto;
import com.example.project.dto.token.TokenResDto;
import com.example.project.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TokenService {

    private final JwtTokenUtil jwtTokenUtil;

    private final String REFRESH_TOKEN = "REFRESH_TOKEN";
    private final String USER_ID = "userId";

    public TokenResDto reissueToken(HttpSession session, ReissueTokenReqDto request) {

        String refreshToken = request.getRefreshToken();
        Long userId = jwtTokenUtil.extractClaims(refreshToken).get(USER_ID, Long.class);
        System.out.println("userId = " + userId);
        TokenResDto tokenResDto = jwtTokenUtil.generateToken(userId);
        session.setAttribute(REFRESH_TOKEN, tokenResDto.getRefreshToken());
        return tokenResDto;
    }

}
