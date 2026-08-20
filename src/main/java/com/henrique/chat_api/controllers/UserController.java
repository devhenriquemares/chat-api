package com.henrique.chat_api.controllers;

import com.henrique.chat_api.dtos.user.CreateLocalUserDTO;
import com.henrique.chat_api.dtos.user.UserResponse;
import com.henrique.chat_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateLocalUserDTO request) {
        UserResponse response = userService.store(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
