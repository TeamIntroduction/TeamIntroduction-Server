package com.example.project.controller;

import com.example.project.dto.LoginReqDto;
import com.example.project.dto.ResponseDto;
import com.example.project.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static com.example.project.constant.SuccessResponse.LOGIN;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(HttpSession session, @RequestBody LoginReqDto request) throws Exception {

        return new ResponseEntity<>(ResponseDto.success(LOGIN, userService.login(session, request)), HttpStatus.OK);
    }
}
