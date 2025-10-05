package com.authorization.authorization.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.authorization.authorization.entity.BaseResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalException {
        // private final ObjectMapper objectMapper = new ObjectMapper();

        // Handle custom BaseException (Unauthorized, Forbidden, etc)
        @ExceptionHandler(BaseException.class)
        public ResponseEntity<BaseResponse<Object>> handleBaseException(BaseException ex) {
                BaseResponse<Object> response = BaseResponse.error(ex.getHttpStatus(), ex.getMessage());
                return ResponseEntity.status(ex.getHttpStatus()).body(response);
        }

        // Handle Spring Security AccessDenied (403)
        @ExceptionHandler(AccessDeniedException.class)
        public ResponseEntity<BaseResponse<Object>> handleAccessDenied(AccessDeniedException ex) {
                BaseResponse<Object> response = BaseResponse.error(HttpStatus.FORBIDDEN, "Access denied");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        // Handle generic runtime exceptions (null pointer, etc)
        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<BaseResponse<Object>> handleRuntime(RuntimeException ex) {
                BaseResponse<Object> response = BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        // Handle any uncaught exception
        @ExceptionHandler(Exception.class)
        public ResponseEntity<BaseResponse<Object>> handleGeneral(Exception ex) {
                BaseResponse<Object> response = BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR,
                                "Internal Server Error");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
}
