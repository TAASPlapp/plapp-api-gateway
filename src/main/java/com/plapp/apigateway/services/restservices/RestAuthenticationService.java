package com.plapp.apigateway.services.restservices;

import com.plapp.apigateway.services.AuthenticationService;
import com.plapp.entities.auth.UserCredentials;
import com.plapp.entities.utils.ApiResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.Response;

@Component
public class RestAuthenticationService implements AuthenticationService {
    @Value("${services.authentication.serviceAddress}")
    private String serviceAddress;

    @Override
    public ApiResponse<UserCredentials> registerUser(UserCredentials credentials) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<UserCredentials> credentialsHttpEntity = new HttpEntity<>(credentials);

        return restTemplate.exchange(
                serviceAddress + "/auth/signup",
                HttpMethod.POST,
                credentialsHttpEntity,
                new ParameterizedTypeReference<ApiResponse<UserCredentials>>(){}).getBody(); //ApiResponse.class<UserCredentials);
    }

    @Override
    public ApiResponse<String> authenticateUser(UserCredentials credentials) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(serviceAddress + "/auth/login", credentials, ApiResponse.class);
    }

    /*@Override
    public ApiResponse authorize(String jwt) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(serviceAddress + "/auth/authorize", jwt, ApiResponse.class);
    }*/
}
