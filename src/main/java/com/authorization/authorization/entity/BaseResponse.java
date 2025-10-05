package com.authorization.authorization.entity;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {
    private Boolean success;
    private String status;
    private int code;
    private String message;
    private String timestamp;
    private T data;

    // Success methods
    public static <T> BaseResponse<T> success(T data) {
        return BaseResponse.<T>builder()
                .status("SUCCESS")
                .success(true)
                .code(HttpStatus.OK.value())
                .message("Operation completed successfully")
                .data(data)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    public static <T> BaseResponse<T> success(String message, T data) {
        return BaseResponse.<T>builder()
                .status("SUCCESS")
                .success(true)
                .code(HttpStatus.OK.value())
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    public static <T> BaseResponse<T> created(T data) {
        return BaseResponse.<T>builder()
                .status("CREATED")
                .success(true)
                .code(HttpStatus.CREATED.value())
                .message("Resource created successfully")
                .data(data)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    // Error methods
    public static BaseResponse<Object> error(HttpStatus status, String message) {
        return BaseResponse.builder()
                .status("ERROR")
                .success(false)
                .code(status.value())
                .message(message)
                .data(null)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    public static BaseResponse<Object> error(int code, String message) {
        return BaseResponse.builder()
                .status("ERROR")
                .success(false)
                .message(message)
                .code(code)
                .data(null)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }
}
