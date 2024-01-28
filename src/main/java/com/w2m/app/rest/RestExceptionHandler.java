package com.w2m.app.rest;

import com.w2m.app.exception.ConflictException;
import com.w2m.app.exception.ErrorResponse;
import com.w2m.app.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.stream.Collectors;

@RestController
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleException(NotFoundException e, WebRequest request) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                new Date(),
                e.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleException(ConflictException e, WebRequest request) {
        return new ErrorResponse(HttpStatus.CONFLICT.value(),
                new Date(),
                e.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(MethodArgumentNotValidException e, WebRequest request) {
        String fieldError = e.getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                new Date(),
                fieldError,
                request.getDescription(false));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(HttpMessageNotReadableException e, WebRequest request) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                new Date(),
                e.getLocalizedMessage(),
                request.getDescription(false));
    }
}
