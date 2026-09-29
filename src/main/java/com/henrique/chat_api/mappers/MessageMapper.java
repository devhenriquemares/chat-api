package com.henrique.chat_api.mappers;

import com.henrique.chat_api.dtos.message.MessageResponseDTO;
import com.henrique.chat_api.entities.Message;

public class MessageMapper {
    public static MessageResponseDTO mapToResponse(Message message) {
        return new MessageResponseDTO(
                message.getSender().getId(),
                message.getId(),
                message.getMessage(),
                message.getTimestamp()
        );
    }
}
