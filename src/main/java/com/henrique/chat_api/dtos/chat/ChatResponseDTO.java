package com.henrique.chat_api.dtos.chat;

import com.henrique.chat_api.dtos.message.MessageResponseDTO;
import com.henrique.chat_api.dtos.user.UserResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Response from Chat routes")
public record ChatResponseDTO(
        Long chatID,
        UserResponseDTO friend,
        List<MessageResponseDTO> messages
) {
}
