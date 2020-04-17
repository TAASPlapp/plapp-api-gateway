package com.plapp.apigateway.services.config;

import com.plapp.apigateway.entities.SessionToken;
import com.plapp.apigateway.saga.UserCreationSagaOrchestrator;
import com.plapp.apigateway.services.SessionTokenService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtRestTemplateBuilder {
    private Logger logger = LoggerFactory.getLogger(JwtRestTemplateBuilder.class);

    private final SessionTokenService sessionTokenService;

    @Bean
    @RequestScope
    public RestTemplate buildWithJwt(HttpServletRequest inReq) {
        final String authHeader = inReq.getHeader(HttpHeaders.AUTHORIZATION);
        final RestTemplate restTemplate = new RestTemplate();

        restTemplate.getInterceptors().add(
                (outRequest, bytes, clientHttpReqExec) -> {
                    String sessionTokenHeader = authHeader;
                    if (sessionTokenHeader == null || sessionTokenHeader.isEmpty()) {
                        logger.info("No session token header in incoming request, checking RequestContext");
                        sessionTokenHeader = SessionRequestContext.getSessionTokenHeader();
                    }

                    if (sessionTokenHeader != null && !sessionTokenHeader.isEmpty()) {
                        logger.info("Session token header: " + sessionTokenHeader);
                        sessionTokenHeader = sessionTokenHeader.replace("Bearer ", "");

                        String jwt = sessionTokenService.getJwt(sessionTokenHeader);
                        outRequest.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
                    }

                    return clientHttpReqExec.execute(outRequest, bytes);
                }
        );

        return restTemplate;
    }
}
