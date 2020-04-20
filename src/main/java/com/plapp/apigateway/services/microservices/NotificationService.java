package com.plapp.apigateway.services.microservices;

public interface NotificationService {
    void registerDevice(long sessionId, long userId, String firebaseToken);
}
