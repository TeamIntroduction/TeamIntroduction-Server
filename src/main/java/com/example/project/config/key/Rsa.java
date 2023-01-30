package com.example.project.config.key;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

@RequiredArgsConstructor
@Component
public class Rsa {

    private final int KEY_SIZE = 2048;
    private final String ALG = "RSA";
    private final HttpSession httpSession;

    public KeyPair generateKey() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance(ALG);
        generator.initialize(KEY_SIZE);
        return generator.genKeyPair();
    }

    public String encrypt(String message, PublicKey publicKey) throws Exception {
        byte[] messageToBytes = message.getBytes();
        Cipher cipher = Cipher.getInstance(ALG);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(messageToBytes);
        return encode(encryptedBytes);
    }

    private String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public String decrypt(String encryptedMessage, PrivateKey privateKey) throws Exception {
        byte[] encryptedBytes = decode(encryptedMessage);
        Cipher cipher = Cipher.getInstance(ALG);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedMessage = cipher.doFinal(encryptedBytes);
        return new String(decryptedMessage, StandardCharsets.UTF_8);
    }

    private byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }
}
