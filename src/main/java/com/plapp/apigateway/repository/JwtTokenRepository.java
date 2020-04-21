package com.plapp.apigateway.repository;

import com.plapp.apigateway.entities.SessionTokenMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JwtTokenRepository extends JpaRepository<SessionTokenMapping.JwtToken, Long> {
    List<SessionTokenMapping.JwtToken> findAllByUserId(Long userId);
}
