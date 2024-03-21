package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorMessage {

    private LocalDateTime date;

    private String message;

    private int status;

}
