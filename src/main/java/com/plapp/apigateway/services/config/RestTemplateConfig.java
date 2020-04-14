package com.plapp.apigateway.services.config;

import com.plapp.apigateway.saga.UserCreationSagaOrchestrator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Component
public class RestTemplateConfig {
    private Logger logger = LoggerFactory.getLogger(RestTemplateConfig.class);

    @Bean
    @RequestScope
    public RestTemplate keycloakRestTemplate(HttpServletRequest inReq) {
        logger.info("keycloaking RestTemplate");
        logger.info("request headers: ");
        for (String headerName : Collections.list(inReq.getHeaderNames()))
            logger.info(headerName + ": " + inReq.getHeader(headerName));


        // retrieve the auth header from incoming request
        final String authHeader =
                inReq.getHeader(HttpHeaders.AUTHORIZATION);
        final RestTemplate restTemplate = new RestTemplate();

        // add a token if an incoming auth header exists, only
        if (authHeader != null && !authHeader.isEmpty()) {
            // since the header should be added to each outgoing request,
            // add an interceptor that handles this.
            restTemplate.getInterceptors().add(
                    (outReq, bytes, clientHttpReqExec) -> {
                        outReq.getHeaders().set(
                                HttpHeaders.AUTHORIZATION, authHeader
                        );
                        return clientHttpReqExec.execute(outReq, bytes);
                    });
        }
        return restTemplate;
    }
}
