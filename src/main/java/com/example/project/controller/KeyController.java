package com.example.project.controller;

import com.example.project.dto.ResponseDto;
import com.example.project.dto.key.SymmetricKeyReqDto;
import com.example.project.service.KeyService;
import com.example.project.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor()
@RequestMapping("/key")
public class KeyController {

    private final KeyService keyService;

    @PostMapping("/asymmetric-key")
    public ResponseDto generateKey(HttpSession session) throws Exception {

        return ResponseUtil.SUCCESS("키 생성 완료", keyService.generateKey(session));
    }

    @PostMapping("/symmetric-key")
    public ResponseDto storeSymmetricKey(HttpSession session, @RequestBody SymmetricKeyReqDto symmetricKeyReqDto) throws Exception {

        keyService.storeSymmetricKey(session, symmetricKeyReqDto);
        return ResponseUtil.SUCCESS("대칭키 저장 완료", null);
    }
}
