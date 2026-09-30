package com.henrique.chat_api.dtos.user;

import com.henrique.chat_api.enums.AccountProviders;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

@Schema(description = "Response from UserController routes")
public record UserResponseDTO(
        UUID userID,
        String username,
        String email,
        AccountProviders provider,
        Instant createdAt
) {
}
