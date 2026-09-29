package com.henrique.chat_api.dtos.user;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperties;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import jakarta.validation.constraints.*;

@Schema(name = "Create local user DTO", description = "Create a new local user, different from a google user")
public record CreateLocalUserDTO (
        @NotBlank
        @Size(min = 6, max = 255)
        @Schema(name = "Username", example = "New User", type = "String", )
        String username,

        @Email
        @NotBlank
        @Schema(name = "Username", example = "New User", type = "String")
        String email,

        @NotBlank
        @Size(min = 8, max = 255)
        String password
) {
}
