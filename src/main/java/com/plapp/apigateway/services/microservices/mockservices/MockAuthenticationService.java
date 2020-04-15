package com.plapp.apigateway.services.microservices.mockservices;

import com.plapp.apigateway.services.microservices.AuthenticationService;
import com.plapp.entities.auth.UserCredentials;

public class MockAuthenticationService implements AuthenticationService {

    @Override
    public UserCredentials registerUser(UserCredentials credentials) {
        return credentials;
    }

    @Override
    public void deleteUser(UserCredentials credentials) {

    }

    @Override
    public String authenticateUser(UserCredentials credentials) {
        return "ourhardworkbythesewordsguardedpleasedontsteal";
    }
}
