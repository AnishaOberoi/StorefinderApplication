package com.example.storefinderbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AdminAccessRequiredException.class)
    public ResponseEntity<String> handleAdminAccessRequiredException(AdminAccessRequiredException ex) {
        String message = "Only admin users are allowed to add products.";
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
    }
}
