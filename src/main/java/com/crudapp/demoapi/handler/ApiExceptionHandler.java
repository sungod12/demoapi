package com.crudapp.demoapi.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.ConnectException;
import java.net.http.HttpTimeoutException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleConnectException(MethodArgumentNotValidException methodArgumentNotValidException){
        List<FieldError> fieldErrorList=methodArgumentNotValidException.getBindingResult().getFieldErrors();
        List<String> errorMessages=fieldErrorList.stream().map(fieldError -> fieldError.getField()+":"+fieldError.getDefaultMessage()).toList();

        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }
}
