package com.authorization.authorization.entity.models;

public record UserRequest(
        String username,
        String password,
        String email,
        String fullName,
        String phoneNumber) {
}
