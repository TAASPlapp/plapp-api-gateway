package com.plapp.apigateway.services;

import com.plapp.entities.auth.UserCredentials;
import com.plapp.entities.utils.ApiResponse;


public interface AuthenticationService {
    ApiResponse<UserCredentials> registerUser(UserCredentials credentials) throws Exception;
    ApiResponse<String> authenticateUser(UserCredentials credentials) throws Exception;
}
