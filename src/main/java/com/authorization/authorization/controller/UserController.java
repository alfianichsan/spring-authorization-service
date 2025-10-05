package com.authorization.authorization.controller;

import com.authorization.authorization.utils.ResponseHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authorization.authorization.entity.BaseResponse;
import com.authorization.authorization.entity.models.UserRequest;
import com.authorization.authorization.entity.models.UserResponse;
import com.authorization.authorization.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final ResponseHelper responseHelper;
    private final UserService userService;

    public UserController(UserService userService, ResponseHelper responseHelper) {
        this.userService = userService;
        this.responseHelper = responseHelper;
    }

    @PostMapping("")
    public ResponseEntity<BaseResponse<UserResponse>> createUser(@Validated @RequestBody UserRequest request) {
        UserResponse createdUser = userService.creatUser(request);
        return responseHelper.createCreatedResponse(createdUser);
    }

}
