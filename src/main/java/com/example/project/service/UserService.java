package com.example.project.service;

import com.example.project.domain.user.User;
import com.example.project.dto.LoginReqDto;
import com.example.project.dto.token.TokenResDto;
import com.example.project.dto.user.UserAuthenticationDto;
import com.example.project.exception.InvalidException;
import com.example.project.repository.UserRepository;
import com.example.project.utils.JwtTokenUtil;
import com.example.project.utils.key.AES;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

import static com.example.project.constant.ErrorResponse.FAIL_LOGIN;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    public TokenResDto login(HttpSession session, LoginReqDto request) throws Exception {

        User user = certify(session, request);

        TokenResDto tokenResDto = jwtTokenUtil.generateToken(user.getId());
        return tokenResDto;
    }

    private User certify(HttpSession session, LoginReqDto request) throws Exception {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new InvalidException(FAIL_LOGIN));

        String symmetricKey = (String) session.getAttribute("SYMMETRIC_KEY");

        String password = AES.decrypt(symmetricKey, request.getPassword());
        checkPassword(password, user);
        return user;
    }

    private void checkPassword(String password, User user) {

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidException(FAIL_LOGIN);
        }
    }
}
