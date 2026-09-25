package com.henrique.chat_api.dtos.auth;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokensDTO(
        @NotBlank()
        String refreshToken
) {
}
