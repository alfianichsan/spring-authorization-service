package com.authorization.authorization.utils;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.authorization.authorization.entity.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public CustomAuthEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException {

        UnauthorizedException unauthorized = new UnauthorizedException(
                authException.getMessage() != null ? authException.getMessage() : "Invalid or missing token");

        BaseResponse<Object> errorResponse = BaseResponse.error(
                unauthorized.getHttpStatus(),
                unauthorized.getMessage());

        response.setStatus(unauthorized.getHttpStatus().value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
