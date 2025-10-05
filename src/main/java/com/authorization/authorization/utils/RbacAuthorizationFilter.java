package com.authorization.authorization.utils;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.authorization.authorization.entity.BaseResponse;
import com.authorization.authorization.service.AuthorizationService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//? Function ini digunakan untuk validasi api dan role
@Component
public class RbacAuthorizationFilter extends OncePerRequestFilter {
    private final AuthorizationService authorizationService;
    private final JwtUtils jwtUtils;
    private final ObjectMapper objectMapper;

    public RbacAuthorizationFilter(AuthorizationService authorizationService, JwtUtils jwtUtils,
            ObjectMapper objectMapper) {
        this.authorizationService = authorizationService;
        this.jwtUtils = jwtUtils;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws IOException, ServletException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (jwtUtils.validateToken(token)) {
                Long userId = jwtUtils.extractUserId(token);

                String path = request.getRequestURI();
                String method = request.getMethod();

                boolean allowed = authorizationService.isAllowedAccess(userId, path, method);

                if (!allowed) {
                    ForbiddenException forbidden = new ForbiddenException("You don't have access");
                    writeErrorResponse(response, forbidden);
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private void writeErrorResponse(HttpServletResponse response, BaseException ex) throws IOException {
        BaseResponse<Object> errorResponse = BaseResponse.error(ex.getHttpStatus(), ex.getMessage());
        response.setStatus(ex.getHttpStatus().value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

}
