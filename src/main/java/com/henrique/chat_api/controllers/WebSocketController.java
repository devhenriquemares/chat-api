package com.henrique.chat_api.controllers;

import com.henrique.chat_api.dtos.websocket.SendMessageDTO;
import com.henrique.chat_api.entities.Friend;
import com.henrique.chat_api.entities.Message;
import com.henrique.chat_api.entities.UserAccount;
import com.henrique.chat_api.repositories.IFriendRepository;
import com.henrique.chat_api.repositories.IMessageRepository;
import com.henrique.chat_api.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketController {
    private final IMessageRepository messageRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final IUserRepository userRepository;
    private final IFriendRepository friendRepository;

    @MessageMapping("/chat/send")
    public void sendMessage(@Payload SendMessageDTO payload) {
        Friend chat = friendRepository.findById(payload.chatID())
                .orElseThrow(ChatNotFoundException::new);

        UserAccount sender = userRepository.findById(payload.senderID())
                .orElseThrow(UserNotFoundException::new);

        Message message = new Message();
        message.setMessage(payload.message());
        message.setChat(chat);
        message.setSender(sender);
        messageRepository.save(message);

        messagingTemplate.convertAndSend("/topic/chat/" + chat.getId(), payload);
    }
}
