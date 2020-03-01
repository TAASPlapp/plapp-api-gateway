package com.plapp.apigateway.services.mockservices;

import com.plapp.apigateway.services.AuthenticationService;
import com.plapp.entities.auth.UserCredentials;
import com.plapp.entities.utils.ApiResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public class MockAuthenticationService implements AuthenticationService {

    @Override
    public ApiResponse<UserCredentials> registerUser(UserCredentials credentials) throws Exception {
        return new ApiResponse<>();
    }

    @Override
    public ApiResponse<String> authenticateUser(UserCredentials credentials) throws Exception {
        return new ApiResponse<>("ourhardworkbythesewordsguardedpleasedontsteal");
    }

    /*@Override
    public ApiResponse authorize(String jwt) throws Exception {
        return new ApiResponse();
    }*/
}
