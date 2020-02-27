package com.plapp.apigateway.services.mockservices;

import com.plapp.apigateway.services.AuthenticationService;
import com.plapp.entities.auth.UserCredentials;
import com.plapp.entities.utils.ApiResponse;
import io.jsonwebtoken.Claims;

public class MockAuthenticationService implements AuthenticationService {

    @Override
    public ApiResponse registerUser(UserCredentials credentials) throws Exception {
        return new ApiResponse();
    }

    @Override
    public ApiResponse authenticateUser(UserCredentials credentials) throws Exception {
        return new ApiResponse(true, "ourhardworkbythesewordsguardedpleasedontsteal");
    }

    @Override
    public Claims authorize(String jwt) throws Exception {
        return null;
    }
}
