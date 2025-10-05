package com.authorization.authorization.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.authorization.authorization.entity.BaseResponse;

@Component
public class ResponseHelper {
    public <T> ResponseEntity<BaseResponse<T>> createSuccessResponse(T data) {
        return ResponseEntity.ok(BaseResponse.success(data));
    }

    public <T> ResponseEntity<BaseResponse<T>> createSuccessResponse(String message, T data) {
        return ResponseEntity.ok(BaseResponse.success(message, data));
    }

    public <T> ResponseEntity<BaseResponse<T>> createCreatedResponse(T data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.created(data));
    }

    // public <T> ResponseEntity<PaginatedResponse<T>> createPaginatedResponse(T
    // data, Page<?> page) {
    // return ResponseEntity.ok(PaginatedResponse.success(data, page));
    // }

    public ResponseEntity<BaseResponse<Object>> createErrorResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(BaseResponse.error(status, message));
    }
}
