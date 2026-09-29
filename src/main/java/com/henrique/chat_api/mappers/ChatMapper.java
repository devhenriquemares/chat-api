package com.henrique.chat_api.mappers;

import com.henrique.chat_api.dtos.chat.ChatResponseDTO;
import com.henrique.chat_api.dtos.message.MessageResponseDTO;
import com.henrique.chat_api.entities.Friend;
import com.henrique.chat_api.entities.Message;

public class ChatMapper {
    public static ChatResponseDTO mapToResponse(Friend chat) {
        return new ChatResponseDTO(
            chat.getId(),
            UserMapper.toResponse(chat.getFriendAccount()),
            chat.getMessages().stream().map(MessageMapper::mapToResponse).toList()
        );
    }

    public static MessageResponseDTO mapToResponse(Message message) {
        return new MessageResponseDTO(
            message.getSender().getId(),
            message.getId(),
            message.getMessage(),
            message.getTimestamp()
        );
    }
}
