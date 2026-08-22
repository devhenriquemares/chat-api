package com.henrique.chat_api.controllers;

import com.henrique.chat_api.dtos.user.CreateLocalUserDTO;
import com.henrique.chat_api.dtos.user.UpdateLocalUserDTO;
import com.henrique.chat_api.dtos.user.UserResponseDTO;
import com.henrique.chat_api.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findByID(@PathVariable(name = "id") UUID userID) {
        UserResponseDTO response = userService.findByID(userID);
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
