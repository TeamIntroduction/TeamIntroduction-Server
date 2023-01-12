package com.example.project.controller;

import com.example.project.dto.ResponseDto;
import com.example.project.dto.token.ReissueTokenReqDto;
import com.example.project.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static com.example.project.constant.SuccessResponse.GENERATE_ASYMMETRIC_KEY;

@RestController
@RequiredArgsConstructor()
@RequestMapping("/token")
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseDto> reissueToken(HttpSession session, @RequestBody ReissueTokenReqDto request) {

        return new ResponseEntity<>(ResponseDto.success(GENERATE_ASYMMETRIC_KEY, tokenService.reissueToken(session, request)), HttpStatus.CREATED);
    }
}
