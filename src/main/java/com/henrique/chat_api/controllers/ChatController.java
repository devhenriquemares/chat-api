package com.henrique.chat_api.controllers;

import com.henrique.chat_api.dtos.chat.ChatResponseDTO;
import com.henrique.chat_api.dtos.error.ErrorResponseDTO;
import com.henrique.chat_api.entities.Friend;
import com.henrique.chat_api.entities.Message;
import com.henrique.chat_api.entities.UserAccount;
import com.henrique.chat_api.mappers.ChatMapper;
import com.henrique.chat_api.repositories.IFriendRepository;
import com.henrique.chat_api.services.AuthService;
import com.henrique.chat_api.services.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "ChatController")
@SecurityRequirement(name = "bearer token")
public class ChatController {
    private final ChatService chatService;

    @GetMapping
    @Operation(summary = "Load all user chats by their token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of chat successfully returned"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<Set<ChatResponseDTO>> loadAllChats() {
        Set<ChatResponseDTO> response = chatService.loadAllChatsBy(AuthService.getAuthenticationPrincipal());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/messages/{id}")
    @Operation(summary = "Load all chat messages by chat ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Messages successfully returned"),
            @ApiResponse(responseCode = "404", description = "No chat found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<List<Message>> loadChatMessages(@PathVariable("id") Long chatID) {
        List<Message> response = chatService.loadChatMessagesBy(chatID);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
