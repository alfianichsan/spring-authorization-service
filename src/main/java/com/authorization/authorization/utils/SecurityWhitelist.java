package com.authorization.authorization.utils;

import org.springframework.stereotype.Component;

@Component
public class SecurityWhitelist {
    public static final String[] ENDPOINTS = {
            "/api/v1/auth/register",
            "/api/v1/auth/login",
            "/v3/api-docs",
            "/v3/api-docs/*",
            "/swagger-ui/*",
            "/swagger-ui.html",
            "/swagger-ui/index.html",
            "/swagger-ui/*",
            "/webjars/*",
            "/swagger-resources/*"
    };

    public boolean isWhitelisted(String path) {
        for (String pattern : ENDPOINTS) {
            if (path.startsWith(pattern)) {
                return true;
            }
        }
        return false;
    }
}
