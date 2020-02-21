package com.plapp.apigateway.services.mockservices;

import com.plapp.apigateway.controllers.ApiResponse;
import com.plapp.apigateway.services.AuthenticationService;
import com.plapp.entities.auth.UserCredentials;
import io.jsonwebtoken.Claims;

public class MockAuthenticationService implements AuthenticationService {

    @Override
    public ApiResponse registerUser(UserCredentials credentials) throws Exception {
        return new ApiResponse();
    }

    @Override
    public String authenticateUser(UserCredentials credentials) throws Exception {
        return "ourhardworkbythesewordsguardedpleasedontsteal";
    }

    @Override
    public Claims authorize(String jwt) throws Exception {
        return null;
    }
}
