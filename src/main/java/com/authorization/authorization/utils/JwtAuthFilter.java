package com.authorization.authorization.utils;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.authorization.authorization.entity.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//? Function ini digunakan untuk validasi Bearer token
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final ObjectMapper objectMapper;
    private final SecurityWhitelist securityWhitelist;

    public JwtAuthFilter(JwtUtils jwtUtils, ObjectMapper objectMapper, SecurityWhitelist securityWhitelist) {
        this.jwtUtils = jwtUtils;
        this.objectMapper = objectMapper;
        this.securityWhitelist = securityWhitelist;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        // ✅ Skip JWT validation if path is in WHITELIST
        if (securityWhitelist.isWhitelisted(path)) {
            filterChain.doFilter(request, response);
            return;
        }
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            UnauthorizedException unauthorized = new UnauthorizedException("Unauthorized");
            writeErrorResponse(response, unauthorized);
            return;
        }

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (jwtUtils.validateToken(token)) {
                String username = jwtUtils.extractUsername(token);

                UserDetails userDetails = User
                        .withUsername(username)
                        .password("") // tidak perlu
                        // .authorities("USER")
                        .build();

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                UnauthorizedException unauthorized = new UnauthorizedException("Invalid or expired token");
                writeErrorResponse(response, unauthorized);
                return;
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
