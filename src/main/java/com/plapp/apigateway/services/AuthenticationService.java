package com.plapp.apigateway.services;

import com.plapp.entities.auth.UserCredentials;
import com.plapp.entities.utils.ApiResponse;
import io.jsonwebtoken.Claims;

public interface AuthenticationService {
    ApiResponse registerUser(UserCredentials credentials) throws Exception;
    ApiResponse authenticateUser(UserCredentials credentials) throws Exception;
    Claims authorize(String jwt) throws Exception;
}
