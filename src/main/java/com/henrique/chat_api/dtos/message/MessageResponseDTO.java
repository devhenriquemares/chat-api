package com.henrique.chat_api.dtos.message;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

@Schema(description = "Message data returned on chat routes")
public record MessageResponseDTO(
        @Schema(description = "Who sent the message")
        UUID senderID,
        UUID messageID,
        String message,
        @Schema(description = "Date the message was sent")
        Instant timestamp
) {
}
