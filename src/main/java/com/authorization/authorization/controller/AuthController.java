package com.authorization.authorization.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authorization.authorization.entity.BaseResponse;
import com.authorization.authorization.entity.models.LoginRequest;
import com.authorization.authorization.entity.models.LoginResponse;
import com.authorization.authorization.service.AuthService;
import com.authorization.authorization.utils.ResponseHelper;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final ResponseHelper responseHelper;

    public AuthController(AuthService authService, ResponseHelper responseHelper) {
        this.authService = authService;
        this.responseHelper = responseHelper;
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        LoginResponse token = authService.login(request);
        return responseHelper.createSuccessResponse(token);
    }
}
