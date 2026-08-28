package com.henrique.chat_api.controllers;

import com.henrique.chat_api.dtos.chat.ChatResponseDTO;
import com.henrique.chat_api.entities.Friend;
import com.henrique.chat_api.entities.Message;
import com.henrique.chat_api.entities.UserAccount;
import com.henrique.chat_api.mappers.ChatMapper;
import com.henrique.chat_api.repositories.IFriendRepository;
import com.henrique.chat_api.services.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chats")
@PreAuthorize("hasAuthority('USER')")
public class ChatController {
    private final ChatService chatService;

    @GetMapping
    public ResponseEntity<Set<ChatResponseDTO>> loadAllChats() {
        UserAccount user = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<ChatResponseDTO> response = chatService.loadAllChatsBy(user);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<List<Message>> loadChatMessages(@PathVariable("id") Long chatID) {
        List<Message> response = chatService.loadChatMessagesBy(chatID);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
