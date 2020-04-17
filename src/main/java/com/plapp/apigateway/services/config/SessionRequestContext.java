package com.plapp.apigateway.services.config;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Objects;

public class SessionRequestContext {
    public static String getSessionTokenHeader() {
            return (String) Objects.requireNonNull(RequestContextHolder
                    .getRequestAttributes())
                    .getAttribute("sessionToken", RequestAttributes.SCOPE_REQUEST);
    }
}
