package com.plapp.apigateway.services;

import com.plapp.entities.auth.UserCredentials;
import com.plapp.entities.utils.ApiResponse;


public interface AuthenticationService {
    UserCredentials registerUser(UserCredentials credentials) throws Exception;
    void deleteUser(UserCredentials credentials) throws Exception;

    String authenticateUser(UserCredentials credentials) throws Exception;
}
