package com.plapp.apigateway.services;

import com.plapp.entities.auth.UserCredentials;

public interface AuthenticationService {
    void registerUser(UserCredentials credentials);
    String authenticateUser(UserCredentials credentials);
    void authorize(String jwt);
}
