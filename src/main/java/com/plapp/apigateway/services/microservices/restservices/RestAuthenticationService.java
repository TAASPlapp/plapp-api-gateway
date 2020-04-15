package com.plapp.apigateway.services.microservices.restservices;

import com.plapp.apigateway.services.microservices.AuthenticationService;
import com.plapp.entities.auth.UserCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestAuthenticationService implements AuthenticationService {
    @Value("${services.authentication.serviceAddress}")
    private String serviceAddress;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public UserCredentials registerUser(UserCredentials credentials) {
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
    public void deleteUser(UserCredentials credentials) {
        restTemplate.postForObject(
                serviceAddress + String.format("/auth/%d/delete", credentials.getId()),
                credentials,
                Void.class
        );
    }

    @Override
    public String authenticateUser(UserCredentials credentials) {
        HttpEntity<UserCredentials> credentialsHttpEntity = new HttpEntity<>(credentials);

        String jwt = restTemplate.postForObject(
            serviceAddress + "/auth/login",
                credentialsHttpEntity,
                String.class
        );

        assert jwt != null;
        return jwt;
    }
}
