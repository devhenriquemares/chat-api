package com.henrique.chat_api.dtos.error;

public record FieldErrorDTO(
        String field,
        String error
) {
}
