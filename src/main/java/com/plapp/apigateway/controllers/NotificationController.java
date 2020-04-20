package com.plapp.apigateway.controllers;

import com.plapp.apigateway.services.config.SessionRequestContext;
import com.plapp.apigateway.services.microservices.NotificationService;
import com.plapp.entities.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    final NotificationService notificationService;

    @CrossOrigin
    @PostMapping("register")
    public ApiResponse<?> register(@RequestParam  String firebaseToken) {
        notificationService.registerDevice(SessionRequestContext.getSessionId(), SessionRequestContext.getCurrentUserId(), firebaseToken);
        return new ApiResponse<>(true);
    }
}
