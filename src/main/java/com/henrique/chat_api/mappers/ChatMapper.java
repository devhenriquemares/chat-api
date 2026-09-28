package com.henrique.chat_api.mappers;

import com.henrique.chat_api.dtos.chat.ChatResponseDTO;
import com.henrique.chat_api.dtos.chat.MessageResponseDTO;
import com.henrique.chat_api.entities.Friend;
import com.henrique.chat_api.entities.Message;

import java.util.stream.Collectors;

public class ChatMapper {
    public static ChatResponseDTO mapToResponse(Friend chat) {
        return new ChatResponseDTO(
            chat.getId(),
            UserMapper.toResponse(chat.getFriendAccount()),
            chat.getMessages().stream().map(ChatMapper::mapToResponse).toList()
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
