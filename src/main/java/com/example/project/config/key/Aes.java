package com.example.project.config.key;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RequiredArgsConstructor
@Component
public class Aes {

    private final String ALG_WITH_MODE_AND_PADDING = "AES/CBC/PKCS5Padding";
    private final String ALG = "AES";
    private final String SYMMETRIC_KEY = "SYMMETRIC_KEY";
    private final HttpSession httpSession;


    public String encryptObject(Object data) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        return encrypt(objectMapper.writeValueAsString(data));
    }

    public String encrypt(String text) throws Exception {

        String key = getKey();
        Cipher cipher = Cipher.getInstance(ALG_WITH_MODE_AND_PADDING);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), ALG);
        IvParameterSpec ivParamSpec = new IvParameterSpec(key.substring(0, 16).getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

        byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public String decrypt(String cipherText) throws Exception {

        String key = getKey();

        byte[] decodedBytes = Base64.getDecoder().decode(cipherText.getBytes());

        Cipher cipher = Cipher.getInstance(ALG_WITH_MODE_AND_PADDING);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), ALG);
        IvParameterSpec ivParamSpec = new IvParameterSpec(key.substring(0, 16).getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

        return new String(cipher.doFinal(decodedBytes));
    }

    private String getKey() {
        return (String) httpSession.getAttribute(SYMMETRIC_KEY);
    }
}