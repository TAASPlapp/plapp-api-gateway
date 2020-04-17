package com.plapp.apigateway.repository;

import com.plapp.apigateway.entities.SessionToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionTokenRepository extends JpaRepository<SessionToken, String> {
    SessionToken findBySessionToken(String sessionToken);
    List<SessionToken> findAllByJwt(String jwt);
}
