package com.plapp.apigateway.security;

import com.plapp.apigateway.services.AuthenticationService;
import com.plapp.entities.utils.ApiResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

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
        logger.info("Verifying JWT token");

        String headerAuthorization = request.getHeader("Authorization");

        if (Strings.isEmpty(headerAuthorization) || !headerAuthorization.startsWith("Bearer "))
            return null;

        try {
            String jwt = headerAuthorization.replace("Bearer ", "");

            /*ApiResponse authorizationResponse = authenticationService.authorize(jwt);
            if (authorizationResponse.getSuccess()) {
                Jws<Claims> jws = Jwts.parser().parseClaimsJws(jwt);
                String subject = jws.getBody().getSubject();
                return new UsernamePasswordAuthenticationToken(subject, null);
            }*/

        } catch (Exception e) {
            logger.error(e);
        }

        return null;
    }


}
