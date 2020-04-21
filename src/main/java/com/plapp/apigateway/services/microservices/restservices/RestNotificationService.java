package com.plapp.apigateway.services.microservices.restservices;

import com.plapp.apigateway.services.microservices.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestNotificationService implements NotificationService {
    @Value("${services.notification.serviceAddress}")
    private String baseAddress;

    @Autowired
    public RestTemplate restTemplate;

    @Override
    public void registerDevice(long sessionId, long userId, String firebaseToken) {
        restTemplate.postForObject(
                baseAddress + String.format("/notifications/%d/register", userId),
                null,
                Object.class,
                userId,
                sessionId,
                firebaseToken
        );
    }
}
