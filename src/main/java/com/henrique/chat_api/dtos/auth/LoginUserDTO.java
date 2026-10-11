package com.henrique.chat_api.dtos.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Used to login an existing user")
public record LoginUserDTO(
        @NotBlank
        @Email
        String email,

        @NotBlank
        String password
) {
}
