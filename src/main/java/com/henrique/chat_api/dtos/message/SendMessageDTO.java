package com.henrique.chat_api.dtos.message;

import java.util.UUID;

public record SendMessageDTO(
        UUID senderID,
        Long chatID,
        String message
) {
}
