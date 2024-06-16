package com.crudapp.demoapi.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.ConnectException;
import java.net.http.HttpTimeoutException;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<String> handleConnectException(ConnectException connectException){
        return new ResponseEntity<>("unable to establish connection to 3rd party api", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
