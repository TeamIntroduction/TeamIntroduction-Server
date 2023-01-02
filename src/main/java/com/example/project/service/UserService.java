package com.example.project.service;

import com.example.project.domain.user.User;
import com.example.project.dto.LoginReqDto;
import com.example.project.exception.InvalidException;
import com.example.project.repository.UserRepository;
import com.example.project.utils.key.AES;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void login(HttpSession session, LoginReqDto request) throws Exception {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new InvalidException("로그인 실패"));

        String symmetricKey = (String)session.getAttribute("SYMMETRIC_KEY");

        String password = AES.decrypt(symmetricKey, request.getPassword());
        checkPassword(password, user);
    }

    private void checkPassword(String password, User user) {

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidException("로그인 실패");
        }
    }
}
