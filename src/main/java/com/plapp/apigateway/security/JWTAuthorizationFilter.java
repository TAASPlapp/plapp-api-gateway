package com.plapp.apigateway.security;

import com.plapp.apigateway.services.AuthenticationService;
import io.jsonwebtoken.Claims;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final AuthenticationService authenticationService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, AuthenticationService authenticationService) {
        super(authenticationManager);
        this.authenticationService = authenticationService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Authentication authentication = verifyJWT(request);

        if (authentication != null)
            SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }


    private Authentication verifyJWT(HttpServletRequest request) {
        String headerAuthorization = request.getHeader("Authorization");

        if (Strings.isEmpty(headerAuthorization) || !headerAuthorization.startsWith("Bearer "))
            return null;

        try {
            Claims claims = authenticationService.authorize(headerAuthorization.replace("Bearer ", ""));
            String subject = claims.getSubject();
            return new UsernamePasswordAuthenticationToken(subject, null);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


}
