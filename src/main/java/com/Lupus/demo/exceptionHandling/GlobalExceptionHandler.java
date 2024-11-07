package com.Lupus.demo.exceptionHandling;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {
    public ResponseEntity<String> handleException(Exception e){
        return ResponseEntity.status(500).body("Wystąpił błąd: " + e.getMessage());
    }
}
