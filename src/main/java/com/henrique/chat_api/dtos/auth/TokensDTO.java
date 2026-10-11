package com.henrique.chat_api.dtos.auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Contain the user's jwt tokens")
public record TokensDTO(
        @Schema(description = "Short lived token used to access protected routes on API")
        String accessToken,

        @Schema(description = "Long lived token used to refresh both of tokens")
        String refreshToken
) {
}
