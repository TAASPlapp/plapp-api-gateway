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
import java.security.spec.X509EncodedKeySpec;

@Component
@RequiredArgsConstructor
public class JWTManager {
    private final Logger logger = LoggerFactory.getLogger(JWTManager.class);

    private PublicKey publicKey;

    @PostConstruct
    public void readPublicKey(String publicKeyPath) throws Exception {
        InputStream inputStream = (new ClassPathResource(publicKeyPath)).getInputStream();
        DataInputStream dataInputStream = new DataInputStream(inputStream);

        int availableBytes = dataInputStream.available();
        logger.info("Loading public key from classpath, available bytes: " + availableBytes);

        byte[] keyBytes = new byte[availableBytes];
        dataInputStream.readFully(keyBytes);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        publicKey = keyFactory.generatePublic(keySpec);
        logger.info("Public key loaded");
    }


    public Jws<Claims> decodeJwt(String jwt) throws JwtException {
        return Jwts.parser()
                .setSigningKey(publicKey)
                .parseClaimsJws(jwt);
    }
}
