package com.plapp.apigateway.repository;

import com.plapp.apigateway.entities.SessionToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionTokenRepository extends JpaRepository<SessionToken, String> {
    SessionToken findBySessionToken(String sessionToken);
}
