package com.example.project.controller;

import com.example.project.dto.ResponseDto;
import com.example.project.dto.key.SymmetricKeyReqDto;
import com.example.project.service.KeyService;
import com.example.project.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequiredArgsConstructor
public class KeyController {

    private final KeyService keyService;

    @PostMapping("/asymmetric-key")
    public ResponseDto generateKey(HttpSession session) throws NoSuchAlgorithmException, InvalidKeySpecException {

        return ResponseUtil.SUCCESS("키 생성 완료", keyService.generateKey(session));
    }

    @PostMapping("/symmetric-key")
    public ResponseDto storeSymmetricKey(HttpSession session, @RequestBody SymmetricKeyReqDto symmetricKeyReqDto) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        keyService.storeSymmetricKey(session, symmetricKeyReqDto);
        return ResponseUtil.SUCCESS("대칭키 저장 완료", null);
    }
}
