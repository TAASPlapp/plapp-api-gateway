package com.plapp.apigateway.services.restservices;

import com.plapp.apigateway.services.AuthenticationService;
import com.plapp.entities.auth.UserCredentials;
import com.plapp.entities.utils.ApiResponse;
import io.jsonwebtoken.Claims;
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
    public ApiResponse authenticateUser(UserCredentials credentials) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        ApiResponse response = restTemplate.postForObject(serviceAddress + "/auth/login", credentials, ApiResponse.class);
        return response;
    }

    @Override
    public Claims authorize(String jwt) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(serviceAddress + "/auth/authorize", jwt, Claims.class);
    }
}
