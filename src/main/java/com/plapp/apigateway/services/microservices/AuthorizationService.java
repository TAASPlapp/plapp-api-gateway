package com.plapp.apigateway.services.microservices;

import com.plapp.authorization.ResourceAuthority;

import java.util.List;

public interface AuthorizationService {
    ResourceAuthority addAuthorization(ResourceAuthority authority);
    void removeAuthorization(ResourceAuthority authority);

    List<ResourceAuthority> addAuthorizations(List<ResourceAuthority> authorities);
    void removeAuthorizations(List<ResourceAuthority> authorities);

    List<ResourceAuthority> getAuthorities(Long userId);

    ResourceAuthority updateAuthorization(String urlRegex, Long value);

    String generateUpdatedJwt(String oldJwt);
}
