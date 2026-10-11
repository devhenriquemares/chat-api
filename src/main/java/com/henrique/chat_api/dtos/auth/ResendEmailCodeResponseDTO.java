package com.henrique.chat_api.dtos.auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Contains success message")
public record ResendEmailCodeResponseDTO(
        @Schema(description = "Success message")
        String message
) {
}
