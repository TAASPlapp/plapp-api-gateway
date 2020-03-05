package com.plapp.apigateway.services;

import com.plapp.entities.utils.ApiResponse;

public interface AuthorizationService {
    String addAuthorization(Long userId, String urlRegex, Long value);
    String removeAuthorization(Long userId, String urlRegex, Long value);
}
