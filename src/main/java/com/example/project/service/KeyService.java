package com.example.project.service;

import com.example.project.dto.key.SymmetricKeyReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.HashMap;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class KeyService {

    private final static int KEY_SIZE = 2048;

    public HashMap<String, String> generateKey(HttpSession session) throws NoSuchAlgorithmException, InvalidKeySpecException {

        KeyPair keyPair = generateKey();

        session.setAttribute("PRIVATE_KEY", keyPair.getPrivate());
//        RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
//        String publicKeyModulus = publicSpec.getModulus().toString(16);
//        String publicKeyExponent = publicSpec.getPublicExponent().toString(16);

        HashMap<String, String> map = getPublicKey(keyPair.getPublic());

        return map;
    }

    public void storeSymmetricKey(HttpSession session, SymmetricKeyReqDto symmetricKeyReqDto) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(cipher.DECRYPT_MODE, getPrivateKey(session));
        byte[] symmetricKey = cipher.doFinal(getDecodedData(symmetricKeyReqDto));

        session.setAttribute("SYMMETRIC_KEY", symmetricKey);

        //System.out.println("복호화 = " + new String(symmetricKey));
    }

    private static byte[] getDecodedData(SymmetricKeyReqDto symmetricKeyReqDto) {

        return Base64.getDecoder().decode(symmetricKeyReqDto.getSymmetricKey());
    }

    private static PrivateKey getPrivateKey(HttpSession session) {

        return (PrivateKey) session.getAttribute("PRIVATE_KEY");
    }

    private static HashMap<String, String> getPublicKey(PublicKey publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {

        //KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        //RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);


        HashMap<String, String> map = new HashMap<String, String>();
        //String publicKeyModulus = publicSpec.getModulus().toString(16);
        String stringPublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        map.put("PUBLIC_KEY", stringPublicKey);

        return map;
    }

    private static KeyPair generateKey() throws NoSuchAlgorithmException {

        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(KEY_SIZE);
        return generator.genKeyPair();
    }
}
