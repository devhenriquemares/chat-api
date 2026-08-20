package com.henrique.chat_api.dtos.user;

import com.henrique.chat_api.enums.AccountProviders;

import java.time.Instant;

public record UserResponse(
        String username,
        String email,
        AccountProviders provider,
        Instant createdAt
) {
}
