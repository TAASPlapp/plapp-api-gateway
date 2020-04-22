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
        restTemplate.getForObject(
                baseAddress + String.format("/notifications/%d/register?firebaseToken=%s&sessionId=%d", userId, firebaseToken, sessionId),
                Void.class,
                Object.class
        );
    }
}
