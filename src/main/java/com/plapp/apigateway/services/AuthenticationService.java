package com.plapp.apigateway.services;

import com.plapp.entities.auth.UserCredentials;
import com.plapp.entities.utils.ApiResponse;


public interface AuthenticationService {
    UserCredentials registerUser(UserCredentials credentials);
    void deleteUser(UserCredentials credentials);

    String authenticateUser(UserCredentials credentials);
}
