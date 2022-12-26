package com.example.project.service;

import com.example.project.domain.user.User;
import com.example.project.dto.LoginReqDto;
import com.example.project.exception.InvalidException;
import com.example.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void login(LoginReqDto request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new InvalidException("로그인 실패"));

        checkPassword(request, user);
    }

    private void checkPassword(LoginReqDto request, User user) {

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidException("로그인 실패");
        }
    }
}
