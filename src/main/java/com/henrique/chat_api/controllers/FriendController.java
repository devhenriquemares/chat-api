package com.henrique.chat_api.controllers;

import com.henrique.chat_api.dtos.friend.AddFriendDTO;
import com.henrique.chat_api.repositories.IFriendRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friends")
public class FriendController {

    @PostMapping
    public ResponseEntity<String> addFriend(@Valid @RequestBody AddFriendDTO request) {
        friendRepository.
        return ResponseEntity.status(HttpStatus.OK).body("Friend solicitation sent");
    }
}
