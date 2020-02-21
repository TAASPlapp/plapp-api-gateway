package com.plapp.apigateway.services.restservices;

import com.google.gson.GsonBuilder;
import com.plapp.apigateway.controllers.ApiResponse;
import com.plapp.apigateway.services.AuthenticationService;
import com.plapp.entities.auth.UserCredentials;
import io.jsonwebtoken.Claims;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@ConfigurationProperties(prefix = "services.authentication")
public class RestAuthenticationService implements AuthenticationService {
    private String serviceAddress;

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    @Override
    public ApiResponse registerUser(UserCredentials credentials) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        ApiResponse response = restTemplate.postForObject(serviceAddress + "/auth/signup", credentials, ApiResponse.class);
        return response;
    }

    @Override
    public String authenticateUser(UserCredentials credentials) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        ApiResponse response = restTemplate.postForObject(serviceAddress + "/auth/login", credentials, ApiResponse.class);
        return response.getMessage();
    }

    @Override
    public Claims authorize(String jwt) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        Claims claims = restTemplate.postForObject(serviceAddress + "/auth/authorize", jwt, Claims.class);
        return claims;
    }
}