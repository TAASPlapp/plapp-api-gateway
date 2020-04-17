package com.plapp.apigateway.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.DataInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;

@Component
@RequiredArgsConstructor
public class JWTManager {
    private final Logger logger = LoggerFactory.getLogger(JWTManager.class);

    private PublicKey publicKey;

    @PostConstruct
    public void readPublicKey() throws Exception {
        InputStream inputStream = new ClassPathResource("private.der").getInputStream();
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        int availableBytes = dataInputStream.available();

        logger.info("Loading private key from classpath, available bytes: " + availableBytes);
        byte[] keyBytes = new byte[availableBytes];
        dataInputStream.readFully(keyBytes);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        publicKey = keyFactory.generatePublic(keySpec);
    }


    public Jws<Claims> decodeJwt(String jwt) throws JwtException {
        return Jwts.parser()
                .setSigningKey(publicKey)
                .parseClaimsJws(jwt);
    }
}
