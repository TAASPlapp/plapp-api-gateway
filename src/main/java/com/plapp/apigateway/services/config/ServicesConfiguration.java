package com.plapp.apigateway.services.config;

import com.plapp.apigateway.services.AuthenticationService;
import com.plapp.apigateway.services.GardenerService;
import com.plapp.apigateway.services.GreenhouseService;
import com.plapp.apigateway.services.SocialService;
import com.plapp.apigateway.services.mockservices.MockAuthenticationService;
import com.plapp.apigateway.services.mockservices.MockGardenerService;
import com.plapp.apigateway.services.mockservices.MockGreenhouseService;
import com.plapp.apigateway.services.mockservices.MockSocialService;
import com.plapp.apigateway.services.restservices.RestAuthenticationService;
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
    public GardenerService getGardenerService() {
        return new MockGardenerService();
    }

    @Bean
    @Primary
    public SocialService getSocialService() {
        return new MockSocialService();
    }
}
