package com.bbjcz.edu.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public boolean handle(Exception e) {
        System.out.println(e.getMessage());
        return false;
    }
}
