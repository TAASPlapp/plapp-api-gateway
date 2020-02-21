package com.plapp.apigateway.services;

import com.plapp.entities.auth.UserCredentials;

public interface AuthenticationService {
    void registerUser(UserCredentials credentials) throws Exception;
    String authenticateUser(UserCredentials credentials) throws Exception;
    void authorize(String jwt) throws Exception;
}
