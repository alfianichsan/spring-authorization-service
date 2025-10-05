package com.authorization.authorization.entity.models;

import java.util.Date;

import com.authorization.authorization.entity.User;

import lombok.Builder;

@Builder
public record UserResponse(
        Long id,
        String username,
        String email,
        String fullName,
        String phoneNumber,
        Boolean isActive,
        Date createdAt,
        Date lastLoginAt) {
    public static UserResponse fromEntity(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .isActive(user.getIsActive())
                .createdAt(user.getCreatedAt())
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }
}
