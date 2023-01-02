package com.example.project.utils.key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AES {
    private final static String alg = "AES/CBC/PKCS5Padding";

    public static String decrypt(String key, String cipherText) throws Exception {

        byte[] decodedBytes = Base64.getDecoder().decode(cipherText.getBytes());

        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(key.substring(0, 16).getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

        return new String(cipher.doFinal(decodedBytes));
    }
}