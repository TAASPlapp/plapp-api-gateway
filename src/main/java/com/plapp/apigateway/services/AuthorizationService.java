package com.plapp.apigateway.services;

import com.plapp.authorization.ResourceAuthority;
import com.plapp.entities.utils.ApiResponse;

public interface AuthorizationService {
    String addAuthorization(ResourceAuthority authority);
    String removeAuthorization(ResourceAuthority authority);
}
