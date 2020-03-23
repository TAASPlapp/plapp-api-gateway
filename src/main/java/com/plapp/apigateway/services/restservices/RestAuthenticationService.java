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
    public UserCredentials registerUser(UserCredentials credentials) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<UserCredentials> credentialsHttpEntity = new HttpEntity<>(credentials);

        UserCredentials savedCredentials = restTemplate.postForObject(
                serviceAddress + "/auth/signup",
                credentialsHttpEntity,
                UserCredentials.class
        );

        assert savedCredentials != null;
        return savedCredentials;
    }

    @Override
    public void deleteUser(UserCredentials credentials) throws Exception {
        //TODO
    }

    @Override
    public String authenticateUser(UserCredentials credentials) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        String jwt = restTemplate.postForObject(
                serviceAddress + "/auth/signup",
                credentials,
                String.class
        );

        assert jwt != null;
        return null;
    }
}
