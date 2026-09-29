package com.henrique.chat_api.dtos.message;

import java.time.Instant;
import java.util.UUID;

public record MessageResponseDTO(
        UUID senderID,
        UUID messageID,
        String message,
        Instant timestamp
) {
}
