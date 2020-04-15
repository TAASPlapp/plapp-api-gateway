package com.plapp.apigateway.services.restservices;

import com.plapp.apigateway.services.AuthorizationService;
import com.plapp.authorization.ResourceAuthority;
import com.plapp.entities.greenhouse.Storyboard;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestAuthorizationService implements AuthorizationService {
    @Value("${services.authentication.serviceAddress}")
    private String serviceAddress;

    @Autowired
    public RestTemplate restTemplate;

    @Override
    public ResourceAuthority addAuthorization(ResourceAuthority authority) {
        ResourceAuthority savedAuthority = restTemplate.postForObject(
                serviceAddress + String.format("/auth/%d/add", authority.getUserId()),
                authority,
                ResourceAuthority.class
        );

        assert savedAuthority != null;
        return savedAuthority;
    }

    @Override
    public void removeAuthorization(ResourceAuthority authority) {
        ResponseEntity<Void> response = restTemplate.postForEntity(
                serviceAddress + String.format("/auth/%d/update", authority.getUserId()),
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
    public List<ResourceAuthority> getAuthorities(Long userId) {
        return restTemplate.exchange(
                serviceAddress + String.format("/auth/%d/authorities", userId),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ResourceAuthority>>() {}
        ).getBody();
    }

    @Override
    public ResourceAuthority updateAuthorization(String urlRegex, Long value) {
        return restTemplate.postForObject(
          serviceAddress + "/auth/update",
          null,
          ResourceAuthority.class,
          urlRegex,
          value
        );
    }

    @Override
    public String generateUpdatedJwt(String oldJwt) {
        return restTemplate.postForObject(
                serviceAddress + "/auth/jwt/fetch",
                oldJwt,
                String.class
        );
    }
}
