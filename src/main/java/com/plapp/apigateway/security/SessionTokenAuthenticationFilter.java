package com.plapp.apigateway.security;

import com.plapp.apigateway.entities.SessionTokenMapping;
import com.plapp.apigateway.services.SessionTokenService;
import com.plapp.apigateway.services.config.SessionRequestContext;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


public class SessionTokenAuthenticationFilter extends BasicAuthenticationFilter {
    private final SessionTokenService sessionTokenService;

    public SessionTokenAuthenticationFilter(AuthenticationManager authenticationManager, SessionTokenService sessionTokenService) {
        super(authenticationManager);
        this.sessionTokenService = sessionTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            logger.info("Found authorization header");
            Authentication authentication = this.validateSessionTokenAndAuthorizeRequest(request, response);
            if (authentication != null)
                SecurityContextHolder.getContext().setAuthentication(authentication);
            else
                logger.info("Could not authenticate user");
        } else {
            logger.info("Authorization header not found");
        }

        chain.doFilter(request, response);
    }

    private Authentication validateSessionTokenAndAuthorizeRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sessionToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        sessionToken = sessionToken.replace("Bearer ", "");

        SessionTokenMapping.SessionToken match = sessionTokenService.findBySessionToken(sessionToken);
        if (match != null) {
            logger.info(String.format("Found matching session token for user %d", match.getJwt().getUserId()));
            SessionRequestContext.setSessionToken(match.getSessionToken());
            SessionRequestContext.setSessionId(match.getSessionId());
            return new UsernamePasswordAuthenticationToken(match.getJwt().getUserId(), null, new ArrayList<>());
        }
        return null;
    }
}
