package com.henrique.chat_api.dtos.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Used to generate new access and refresh tokens")
public record RefreshTokensDTO(
        @NotBlank()
        @Schema(description = "Long lived token used to refresh both of tokens")
        String refreshToken
) {
}
