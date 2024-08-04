package com.khasanov.project_rest.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ApplicationException {
    NoSuchElementException(NOT_FOUND, 1),
    MethodArgumentNotValidException(BAD_REQUEST, 2),
    RuntimeException(INTERNAL_SERVER_ERROR, 99);

    private final HttpStatus httpStatus;
    private final int subCode;
    private final int errorCode;

    ApplicationException(HttpStatus httpStatus, int subCode) {
        this.httpStatus = httpStatus;
        this.subCode = subCode;
        this.errorCode = httpStatus.value() * 100 + subCode;
    }

    public static ApplicationException resolve(String name) {
        try {
            return ApplicationException.valueOf(name);
        } catch (IllegalArgumentException e) {
            return RuntimeException;
        }
    }
}
