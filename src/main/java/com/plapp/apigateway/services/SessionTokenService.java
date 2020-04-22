package com.plapp.apigateway.services;

import com.plapp.apigateway.entities.SessionTokenMapping;
import com.plapp.apigateway.repository.JwtTokenRepository;
import com.plapp.apigateway.repository.SessionTokenRepository;
import com.plapp.apigateway.security.JWTManager;
import com.plapp.apigateway.services.config.SessionRequestContext;
import com.plapp.apigateway.services.microservices.AuthorizationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionTokenService {
    private static Logger logger = LoggerFactory.getLogger(SessionTokenService.class);

    private final SessionTokenRepository sessionTokenRepository;
    private final AuthorizationService authorizationService;
    private final JwtTokenRepository jwtTokenRepository;
    private final JWTManager jwtManager;

    public String generateSessionToken(String jwt) {
        Jws<Claims> claims = jwtManager.decodeJwt(jwt);
        Long userId = Long.parseLong(claims.getBody().getSubject());
        SessionTokenMapping.JwtToken jwtToken =  jwtTokenRepository.findById(userId).orElse(null);

        if (jwtToken == null) {
            jwtToken = new SessionTokenMapping.JwtToken();
            jwtToken.setJwt(jwt);
            jwtToken.setUserId(userId);
        }

        SessionTokenMapping.SessionToken sessionToken = new SessionTokenMapping.SessionToken();
        sessionToken.setSessionToken(UUID.randomUUID().toString());
        sessionToken.setJwt(jwtToken);

        logger.info(String.format("Saving session token with uid %d", userId));
        sessionToken = sessionTokenRepository.save(sessionToken);

        SessionRequestContext.setSessionToken(sessionToken.getSessionToken());
        SessionRequestContext.setSessionId(sessionToken.getSessionId());

        return sessionToken.getSessionToken();
    }

    public String getJwt(String sessionToken) {
        SessionTokenMapping.SessionToken session = findBySessionToken(sessionToken);
        return session.getJwt().getJwt();
    }

    public Void deleteSession(String sessionToken) {
        sessionTokenRepository.deleteBySessionToken(sessionToken);
        return null;
    }

    public SessionTokenMapping.SessionToken findBySessionToken(String sessionToken) {
        logger.info("Searching jwt by session token {}", sessionToken);
        SessionTokenMapping.SessionToken session = sessionTokenRepository.findBySessionToken(sessionToken);
        logger.info("Got sessionToken: {}", session);
        return session;
    }

    public Void updateJwt(String jwt) {
        Jws<Claims> claims = jwtManager.decodeJwt(jwt);
        Long userId = Long.parseLong(claims.getBody().getSubject());

        List<SessionTokenMapping.JwtToken> jwtTokens = jwtTokenRepository.findAllByUserId(userId);
        for (SessionTokenMapping.JwtToken jwtToken : jwtTokens)
            jwtToken.setJwt(jwt);

        jwtTokenRepository.saveAll(jwtTokens);

        return null;
    }

    public Void fetchAndUpdateJwt() {
        return updateJwt(
                authorizationService.generateUpdatedJwt(
                        getJwt(SessionRequestContext.getSessionToken())
                )
        );
    }
}
