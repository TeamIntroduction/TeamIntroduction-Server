package com.example.project.controller;

import com.example.project.dto.LoginReqDto;
import com.example.project.dto.ResponseDto;
import com.example.project.service.UserService;
import com.example.project.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseDto login(HttpSession session, @RequestBody LoginReqDto request) throws Exception {

        userService.login(session, request);

        return ResponseUtil.SUCCESS("로그인 완료", null);
    }
}
