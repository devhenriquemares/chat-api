package com.henrique.chat_api.dtos.error;

import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;

public record ErrorResponseDTO(
        int status,
        String code,
        String message,
        List<FieldErrorDTO> errors,
        Instant timestamp
) {
}
