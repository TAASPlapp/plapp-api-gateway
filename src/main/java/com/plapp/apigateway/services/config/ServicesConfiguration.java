package com.plapp.apigateway.services.config;

import com.plapp.apigateway.services.GreenhouseService;
import com.plapp.apigateway.services.mockservices.MockGreenhouseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages="com.plapp.apigateway.controllers")
public class ServicesConfiguration {
    @Bean
    public GreenhouseService getGreenhouseService() {
        return new MockGreenhouseService();
    }
}
