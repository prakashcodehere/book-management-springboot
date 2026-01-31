package com.example.book_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponseDTO {

    private boolean success;
    private String error;
    private String message;
    private LocalDateTime timestamp;
}
