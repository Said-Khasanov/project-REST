package com.khasanov.project_rest.exception;

import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler({
            NoSuchElementException.class,
            MethodArgumentNotValidException.class
    })
    public ErrorResponse handle(Exception ex) {
        ApplicationException applicationException = ApplicationException.resolve(ex.getClass().getSimpleName());
        return ErrorResponse
                .builder(ex, applicationException.getHttpStatus(), ex.getMessage())
                .property("errorCode", applicationException.getErrorCode())
                .build();
    }
}
