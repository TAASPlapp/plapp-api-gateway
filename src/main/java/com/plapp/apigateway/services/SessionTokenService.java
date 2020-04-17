package com.plapp.apigateway.services;

import com.plapp.apigateway.entities.SessionToken;
import com.plapp.apigateway.repository.SessionTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SessionTokenService {
    private final SessionTokenRepository sessionTokenRepository;

    public String generateSessionToken(String jwt) {
        SessionToken sessionToken = new SessionToken();
        sessionToken.setSessionToken(UUID.randomUUID().toString());
        sessionToken.setJwt(jwt);
        sessionTokenRepository.save(sessionToken);

        return sessionToken.getSessionToken();
    }

    public String getJwt(String sessionToken) {
        SessionToken session = sessionTokenRepository.findBySessionToken(sessionToken);
        return session.getJwt();
    }

    public Void deleteSession(SessionToken sessionToken) {
        sessionTokenRepository.delete(sessionToken);
        return null;
    }

    public void updateJwt(String oldJwt, String newJwt) {
        List<SessionToken> sessionTokens = sessionTokenRepository.findAllByJwt(oldJwt);
        for (SessionToken sessionToken : sessionTokens)
            sessionToken.setJwt(newJwt);
        sessionTokenRepository.saveAll(sessionTokens);
    }
}
