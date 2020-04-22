package com.plapp.apigateway.repository;

import com.plapp.apigateway.entities.SessionTokenMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionTokenRepository extends JpaRepository<SessionTokenMapping.SessionToken, Long> {
    SessionTokenMapping.SessionToken findBySessionToken(String sessionToken);
    void deleteBySessionToken(String sessionToken);
}
