package com.khasanov.project_rest.exception;

import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResponse handleAllExceptions(NoSuchElementException ex) {
        ApplicationException applicationException = ApplicationException.resolve(ex.getClass().getSimpleName());
        return ErrorResponse
                .builder(ex, applicationException.getHttpStatus(), ex.getMessage())
                .property("errorCode", applicationException.getErrorCode())
                .build();
    }
}
