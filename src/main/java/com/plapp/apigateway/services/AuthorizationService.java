package com.plapp.apigateway.services;

import com.plapp.authorization.ResourceAuthority;
import com.plapp.entities.auth.UserCredentials;

import java.util.List;

public interface AuthorizationService {
    ResourceAuthority addAuthorization(ResourceAuthority authority);
    void removeAuthorization(ResourceAuthority authority);

    List<ResourceAuthority> addAuthorizations(List<ResourceAuthority> authorities);
    void removeAuthorizations(List<ResourceAuthority> authorities);

    //String generateUpdatedJwt(UserCredentials userDetails);
    String generateUpdatedJwt(String oldJwt);
}
