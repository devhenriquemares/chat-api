package com.henrique.chat_api.dtos.user;

import com.henrique.chat_api.enums.AccountProviders;

import java.time.Instant;
import java.util.UUID;

public record UserResponseDTO(
        UUID userID,
        String username,
        String email,
        AccountProviders provider,
        Instant createdAt
) {
}
