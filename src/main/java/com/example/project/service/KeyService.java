package com.example.project.service;

import com.example.project.dto.key.SymmetricKeyReqDto;
import com.example.project.utils.key.RSA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.HashMap;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class KeyService {

    private final static String PRIVATE_KEY = "PRIVATE_KEY";
    private final static String SYMMETRIC_KEY = "SYMMETRIC_KEY";

    private static HashMap<String, String> createSendingFormatOfPublicKey(PublicKey publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {

        HashMap<String, String> map = new HashMap<String, String>();
        String stringPublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        map.put("PK", stringPublicKey);
        return map;
    }

    public HashMap<String, String> generateKey(HttpSession session) throws Exception {

        KeyPair keyPair = RSA.generateKey();
        session.setAttribute(PRIVATE_KEY, keyPair.getPrivate());
        HashMap<String, String> map = createSendingFormatOfPublicKey(keyPair.getPublic());
        return map;
    }

    public void storeSymmetricKey(HttpSession session, SymmetricKeyReqDto request) throws Exception {

        String symmetricKey = RSA.decrypt(request.getSK(), (PrivateKey)session.getAttribute(PRIVATE_KEY));
        session.setAttribute(SYMMETRIC_KEY, symmetricKey);
    }
}
