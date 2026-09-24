package com.henrique.chat_api.controllers;

import com.henrique.chat_api.dtos.friend.FriendResponseDTO;
import com.henrique.chat_api.dtos.friend.SendFriendRequestDTO;
import com.henrique.chat_api.entities.UserAccount;
import com.henrique.chat_api.services.AuthService;
import com.henrique.chat_api.services.FriendService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friends")
@PreAuthorize("hasAuthority('USER')")
public class FriendController {
    private final FriendService friendService;

    @PostMapping
    public ResponseEntity<Map<String, String>> sendFriendRequest(@Valid @RequestBody SendFriendRequestDTO request) {
        friendService.sendFriendRequest(request, AuthService.getAuthenticationPrincipal());

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Friend solicitation sent"));
    }

    @GetMapping
    public ResponseEntity<Set<FriendResponseDTO>> loadFriendsList() {
        Set<FriendResponseDTO> response = friendService.loadFriendsListBy(AuthService.getAuthenticationPrincipal());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/requests")
    public ResponseEntity<Set<FriendResponseDTO>> loadFriendRequests() {
        UserAccount user = AuthService.getAuthenticationPrincipal();
        Set<FriendResponseDTO> requests = friendService.loadFriendRequestsBy(user);

        return ResponseEntity.status(HttpStatus.OK).body(requests);
    }

    @GetMapping("/accept/{id}")
    public ResponseEntity<String> acceptFriendRequest(@PathVariable("id") Long friendRequestID) {
        friendService.acceptFriendRequest(friendRequestID);

        return ResponseEntity.status(HttpStatus.OK).body("Friend request successfully accepted");
    }

    @GetMapping("/reject/{id}")
    public ResponseEntity<Map<String, String>> rejectFriendRequest(@PathVariable("id") Long friendRequestID) {
        friendService.rejectFriendRequest(friendRequestID);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Friend request successfully rejected"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteFriend(@PathVariable("id") UUID friendID) {
        friendService.deleteFriendByID(friendID);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Friend successfully deleted"));
    }
}
