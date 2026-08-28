package com.henrique.chat_api.dtos.websocket;

import java.util.UUID;

public record SendMessageDTO(
        UUID senderID,
        Long chatID,
        String message
) {
}
