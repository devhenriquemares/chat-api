package com.henrique.chat_api.dtos;

import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;

public record ErrorResponseDTO(
        HttpStatus status,
        String code,
        String message,
        List<String> errors,
        Instant timestamp
) {
}
