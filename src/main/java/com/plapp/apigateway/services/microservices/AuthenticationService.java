package com.plapp.apigateway.services.microservices;

import com.plapp.entities.auth.UserCredentials;


public interface AuthenticationService {
    UserCredentials registerUser(UserCredentials credentials);
    void deleteUser(UserCredentials credentials);

    String authenticateUser(UserCredentials credentials);
}
