package com.authorization.authorization.utils;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {
    private final String errorCode;
    private final HttpStatus httpStatus;

    public BaseException(String message, String errorCode, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public BaseException(String message, String errorCode, HttpStatus httpStatus, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
}
