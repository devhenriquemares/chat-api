package com.henrique.chat_api.dtos;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public record ErrorResponseDTO(
        HttpStatus status,
        String code,
        String message,
        Instant timestamp
) {
}
