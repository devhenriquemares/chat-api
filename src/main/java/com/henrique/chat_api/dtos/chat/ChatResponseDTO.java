package com.henrique.chat_api.dtos.chat;

import com.henrique.chat_api.dtos.message.MessageResponseDTO;
import com.henrique.chat_api.dtos.user.UserResponseDTO;

import java.util.List;

public record ChatResponseDTO(
        Long chatID,
        UserResponseDTO friend,
        List<MessageResponseDTO> messages
) {
}
