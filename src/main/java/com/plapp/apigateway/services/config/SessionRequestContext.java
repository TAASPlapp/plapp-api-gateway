package com.plapp.apigateway.services.config;

import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Objects;

public class SessionRequestContext {
    public static String getSessionTokenHeader() {
            return (String) Objects.requireNonNull(RequestContextHolder
                    .getRequestAttributes())
                    .getAttribute("sessionToken", RequestAttributes.SCOPE_REQUEST);
    }

    public static void setSessionToken(String sessionToken) {
        RequestAttributes attributes = RequestContextHolder.currentRequestAttributes();
        LoggerFactory.getLogger(SessionRequestContext.class).info("Setting jwt attribute for request scope");
        attributes.setAttribute("sessionToken", sessionToken, RequestAttributes.SCOPE_REQUEST);
        RequestContextHolder.setRequestAttributes(attributes);
    }

    public static long getCurrentUserId() {
        return (Long)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
