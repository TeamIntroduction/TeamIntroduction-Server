package com.example.project.service;

import com.example.project.config.key.Aes;
import com.example.project.domain.user.User;
import com.example.project.dto.LoginReqDto;
import com.example.project.dto.token.TokenResDto;
import com.example.project.exception.err40x.InvalidException;
import com.example.project.repository.UserRepository;
import com.example.project.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

import static com.example.project.constant.ErrorResponse.FAIL_LOGIN;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final Aes aes;

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    private final String REFRESH_TOKEN = "REFRESH_TOKEN";
    private final String SYMMETRIC_KEY = "SYMMETRIC_KEY";

    public TokenResDto login(HttpSession session, LoginReqDto request) throws Exception {

        User user = certify(session, request);

        TokenResDto tokenResDto = jwtTokenUtil.generateToken(user.getId());
        session.setAttribute(REFRESH_TOKEN, tokenResDto.getRefreshToken());
        return tokenResDto;
    }

    private User certify(HttpSession session, LoginReqDto request) throws Exception {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new InvalidException(FAIL_LOGIN));

        String symmetricKey = (String) session.getAttribute(SYMMETRIC_KEY);

        String password = aes.decrypt(request.getPassword());
        checkPassword(password, user);
        return user;
    }

    private void checkPassword(String password, User user) {

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidException(FAIL_LOGIN);
        }
    }
}
