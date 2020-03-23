package com.plapp.apigateway.services.restservices;

import com.plapp.apigateway.services.AuthorizationService;
import com.plapp.authorization.ResourceAuthority;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class RestAuthorizationService implements AuthorizationService {
    @Value("${services.authentication.serviceAddress}")
    private String serviceAddress;

    @Override
    public ResourceAuthority addAuthorization(ResourceAuthority authority) {
        RestTemplate restTemplate = new RestTemplate();

        ResourceAuthority savedAuthority = restTemplate.postForObject(
                serviceAddress + String.format("/%d/update", authority.getUserId()),
                authority,
                ResourceAuthority.class
        );

        assert savedAuthority != null;
        return savedAuthority;
    }

    @Override
    public void removeAuthorization(ResourceAuthority authority) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Void> response = restTemplate.postForEntity(
                serviceAddress + String.format("/%d/update", authority.getUserId()),
                authority,
                Void.class
        );
    }

    @Override
    public List<ResourceAuthority> addAuthorizations(List<ResourceAuthority> authorities) {
        List<ResourceAuthority> savedAuthorities = new ArrayList<>();
        for (ResourceAuthority authority : authorities)
            savedAuthorities.add(addAuthorization(authority));
        return savedAuthorities;
    }

    @Override
    public void removeAuthorizations(List<ResourceAuthority> authorities) {
        for(ResourceAuthority authority : authorities)
            removeAuthorization(authority);
    }

    @Override
    public String generateUpdatedJwt(String oldJwt) {
        return oldJwt;
    }
}