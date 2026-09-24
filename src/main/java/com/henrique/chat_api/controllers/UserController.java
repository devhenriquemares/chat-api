package com.henrique.chat_api.controllers;

import com.henrique.chat_api.dtos.friend.FriendResponseDTO;
import com.henrique.chat_api.dtos.user.CreateLocalUserDTO;
import com.henrique.chat_api.dtos.user.UpdateLocalUserDTO;
import com.henrique.chat_api.dtos.user.UserResponseDTO;
import com.henrique.chat_api.entities.UserAccount;
import com.henrique.chat_api.exceptions.ResourceNotFoundException;
import com.henrique.chat_api.mappers.UserMapper;
import com.henrique.chat_api.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    private final UserService userService;

    @GetMapping("/search/{id}")
    public ResponseEntity<UserResponseDTO> findByID(@PathVariable(name = "id") UUID userID) {
        UserResponseDTO response = userService.findByID(userID);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/publicID/{id}")
    public ResponseEntity<UserResponseDTO> findByPublicID(@PathVariable("id") String publicID) {
        UserResponseDTO response = userService.findByPublicID(publicID);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody CreateLocalUserDTO request) {
        UserResponseDTO response = userService.storeAndMap(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateByID(@PathVariable(name = "id") UUID userID, @Valid @RequestBody UpdateLocalUserDTO request) {
        userService.updateByID(userID, request);
        return ResponseEntity.status(HttpStatus.OK).body("User successfully updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteByID(@PathVariable(name = "id") UUID userID) {
        userService.deleteByID(userID);
        return ResponseEntity.status(HttpStatus.OK).body("User successfully deleted");
    }
}
