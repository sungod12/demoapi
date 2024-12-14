package com.crudapp.demoapi.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        List<FieldError> fieldErrorList=methodArgumentNotValidException.getBindingResult().getFieldErrors();
        List<String> errorMessages=fieldErrorList.stream().map(fieldError -> fieldError.getField()+": "+fieldError.getDefaultMessage()).toList();
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }

  /*  @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handle(BadCredentialsException methodArgumentNotValidException){
        String message=methodArgumentNotValidException.getMessage();
//        List<String> errorMessages=fieldErrorList.stream().map(fieldError -> fieldError.getField()+": "+fieldError.getDefaultMessage()).toList();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }*/

}
