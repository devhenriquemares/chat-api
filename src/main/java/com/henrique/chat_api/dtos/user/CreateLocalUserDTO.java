package com.henrique.chat_api.dtos.user;

import jakarta.validation.constraints.*;

public record CreateLocalUserDTO (
        @NotBlank
        @Size(min = 6, max = 255)
        String username,

        @Email
        @NotBlank
        String email,

        @NotBlank
        @Size(min = 8, max = 255)
        String password
) {
}
