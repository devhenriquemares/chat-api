package com.henrique.chat_api.services;

import com.henrique.chat_api.dtos.chat.ChatResponseDTO;
import com.henrique.chat_api.entities.Message;
import com.henrique.chat_api.entities.UserAccount;
import com.henrique.chat_api.exceptions.ChatNotFoundException;
import com.henrique.chat_api.mappers.ChatMapper;
import com.henrique.chat_api.repositories.IFriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final IFriendRepository friendRepository;

    public Set<ChatResponseDTO> loadAllChatsBy(UserAccount user) {
        return friendRepository.findAllByUserAccount(user)
                .stream()
                .map(ChatMapper::mapToResponse)
                .collect(Collectors.toSet());
    }

    public List<Message> loadChatMessagesBy(Long chatID) {
        return friendRepository.findById(chatID)
                .orElseThrow(ChatNotFoundException::new)
                .getMessages();
    }
}
