package com.example.project.utils.key;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AES {
    private final static String ALG_WITH_MODE_AND_PADDING = "AES/CBC/PKCS5Padding";
    private final static String ALG = "AES";

    public static String encryptObject(String key, Object data) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        return encrypt(key, objectMapper.writeValueAsString(data));
    }

    public static String encrypt(String key, String text) throws Exception {

        Cipher cipher = Cipher.getInstance(ALG_WITH_MODE_AND_PADDING);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), ALG);
        IvParameterSpec ivParamSpec = new IvParameterSpec(key.substring(0, 16).getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

        byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String key, String cipherText) throws Exception {

        byte[] decodedBytes = Base64.getDecoder().decode(cipherText.getBytes());

        Cipher cipher = Cipher.getInstance(ALG_WITH_MODE_AND_PADDING);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), ALG);
        IvParameterSpec ivParamSpec = new IvParameterSpec(key.substring(0, 16).getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

        return new String(cipher.doFinal(decodedBytes));
    }
}