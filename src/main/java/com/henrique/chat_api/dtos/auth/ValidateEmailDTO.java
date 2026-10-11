package com.henrique.chat_api.dtos.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@Schema(description = "Used to validate an user email account")
public record ValidateEmailDTO(
        @Size(min = 6, max = 6)
        @Schema(description = "Code invited to user's email when their account is created")
        String code
) {
}
