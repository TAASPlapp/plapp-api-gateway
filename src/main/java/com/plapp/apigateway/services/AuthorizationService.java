package com.plapp.apigateway.services;

import com.plapp.entities.utils.ApiResponse;

public interface AuthorizationService {
    ApiResponse<String> addAuthorization(Long userId, String urlRegex, Long value);
    ApiResponse<String> removeAuthorization(Long userId, String urlRegex, Long value);
}
