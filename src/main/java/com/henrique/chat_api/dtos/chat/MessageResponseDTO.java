package com.henrique.chat_api.dtos.chat;

import com.henrique.chat_api.entities.Friend;
import com.henrique.chat_api.entities.UserAccount;

import java.time.Instant;
import java.util.UUID;

public record MessageResponseDTO(
        UUID senderID,
        String message,
        Instant timestamp
) {
}
