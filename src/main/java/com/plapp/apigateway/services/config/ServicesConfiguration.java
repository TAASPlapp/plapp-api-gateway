package com.plapp.apigateway.services.config;

import com.plapp.apigateway.services.*;
import com.plapp.apigateway.services.mockservices.MockAuthenticationService;
import com.plapp.apigateway.services.mockservices.MockGardenerService;
import com.plapp.apigateway.services.mockservices.MockGreenhouseService;
import com.plapp.apigateway.services.mockservices.MockSocialService;
import com.plapp.apigateway.services.restservices.RestAuthenticationService;
import com.plapp.apigateway.services.restservices.RestAuthorizationService;
import com.plapp.apigateway.services.restservices.RestSocialService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ComponentScan(basePackages="com.plapp.apigateway.controllers")
public class ServicesConfiguration {

    @Bean
    @Primary
    public GreenhouseService getGreenhouseService() {
        return new MockGreenhouseService();
    }

    @Bean
    @Primary
    public AuthenticationService getAuthenticationService() {
        return new RestAuthenticationService();
    }

    @Bean
    @Primary
    public AuthorizationService getAuthorizationService() { return new RestAuthorizationService(); }

    @Bean
    @Primary
    public GardenerService getGardenerService() {
        return new MockGardenerService();
    }

    @Bean
    @Primary
    public SocialService getSocialService() {
        return new RestSocialService();
    }
}
