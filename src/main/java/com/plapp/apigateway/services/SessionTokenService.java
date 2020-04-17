package com.plapp.apigateway.services;

import com.plapp.apigateway.entities.SessionTokenMapping;
import com.plapp.apigateway.repository.JwtTokenRepository;
import com.plapp.apigateway.repository.SessionTokenRepository;
import com.plapp.apigateway.security.JWTManager;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SessionTokenService {
    private final SessionTokenRepository sessionTokenRepository;
    private final JwtTokenRepository jwtTokenRepository;
    private final JWTManager jwtManager;

    public String generateSessionToken(String jwt) {
        SessionTokenMapping.SessionToken sessionToken = new SessionTokenMapping.SessionToken();
        sessionToken.setSessionToken(UUID.randomUUID().toString());

        SessionTokenMapping.JwtToken jwtToken = new SessionTokenMapping.JwtToken();
        jwtToken.setJwt(jwt);

        Jws<Claims> claims = jwtManager.decodeJwt(jwt);
        Long userId = Long.parseLong(claims.getBody().getSubject());

        jwtToken.setUserId(userId);
        sessionToken.setJwt(jwtToken);
        sessionTokenRepository.save(sessionToken);

        return sessionToken.getSessionToken();
    }

    public String getJwt(String sessionToken) {
        SessionTokenMapping.SessionToken session = sessionTokenRepository.findBySessionToken(sessionToken);
        return session.getJwt().getJwt();
    }

    public Void deleteSession(String sessionToken) {
        sessionTokenRepository.deleteById(sessionToken);
        return null;
    }

    public void updateJwt(String jwt) {
        Jws<Claims> claims = jwtManager.decodeJwt(jwt);
        Long userId = Long.parseLong(claims.getBody().getSubject());

        List<SessionTokenMapping.JwtToken> jwtTokens = jwtTokenRepository.findAllByUserId(userId);
        for (SessionTokenMapping.JwtToken jwtToken : jwtTokens)
            jwtToken.setJwt(jwt);

        jwtTokenRepository.saveAll(jwtTokens);
    }
}
