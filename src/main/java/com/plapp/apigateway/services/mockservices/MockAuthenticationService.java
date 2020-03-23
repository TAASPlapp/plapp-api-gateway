package com.plapp.apigateway.services.mockservices;

import com.plapp.apigateway.services.AuthenticationService;
import com.plapp.entities.auth.UserCredentials;
import com.plapp.entities.utils.ApiResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

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
