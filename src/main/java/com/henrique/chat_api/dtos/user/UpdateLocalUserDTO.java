package com.henrique.chat_api.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateLocalUserDTO(
        @Size(min = 6, max = 255)
        String username,

        @Email
        String email,

        @Size(min = 8, max = 255)
        String newPassword,

        @Size(min = 8, max = 255)
        String oldPassword
) {
}
