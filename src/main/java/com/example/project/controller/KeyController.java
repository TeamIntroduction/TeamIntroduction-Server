package com.example.project.controller;

import com.example.project.dto.ResponseDto;
import com.example.project.dto.key.SymmetricKeyReqDto;
import com.example.project.service.KeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static com.example.project.constant.SuccessResponse.GENERATE_ASYMMETRIC_KEY;
import static com.example.project.constant.SuccessResponse.STORE_SYMMETRIC_KEY;

@RestController
@RequiredArgsConstructor()
@RequestMapping("/api/ks")
public class KeyController {

    private final KeyService keyService;

    @PostMapping("/a-k")
    public ResponseEntity<ResponseDto> generateKey(HttpSession session) throws Exception {

        return new ResponseEntity<>(ResponseDto.success(GENERATE_ASYMMETRIC_KEY, keyService.generateKey(session)), HttpStatus.CREATED);
    }

    @PostMapping("/s-k")
    public ResponseEntity<ResponseDto> storeSymmetricKey(HttpSession session, @RequestBody SymmetricKeyReqDto request) throws Exception {

        keyService.storeSymmetricKey(session, request);
        return new ResponseEntity<>(ResponseDto.success(STORE_SYMMETRIC_KEY, null), HttpStatus.CREATED);
    }
}
