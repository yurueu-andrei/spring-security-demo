package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorMessage> handleCustomException(CustomException exception) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                exception.getMessage(), exception.getHttpStatus().value());
        return new ResponseEntity<>(errorMessage, exception.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception exception) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), exception.getMessage(), 400);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }


}
