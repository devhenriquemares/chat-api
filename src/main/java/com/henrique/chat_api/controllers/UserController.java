package com.henrique.chat_api.controllers;

import com.henrique.chat_api.dtos.friend.FriendResponseDTO;
import com.henrique.chat_api.dtos.user.CreateLocalUserDTO;
import com.henrique.chat_api.dtos.user.UpdateLocalUserDTO;
import com.henrique.chat_api.dtos.user.UserResponseDTO;
import com.henrique.chat_api.entities.UserAccount;
import com.henrique.chat_api.exceptions.ResourceNotFoundException;
import com.henrique.chat_api.mappers.UserMapper;
import com.henrique.chat_api.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirementEntry;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "UserController", description = "Users' controller, protected by jwt tokens and authorized only to admins")
@SecurityRequirement(name = "bearer token")
public class UserController {
    private final UserService userService;

    @GetMapping("/search/{id}")
    @Operation(summary = "Search user by ID")
    public ResponseEntity<UserResponseDTO> findByID(@PathVariable(name = "id") UUID userID) {
        UserResponseDTO response = userService.findByID(userID);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/publicID/{id}")
    @Operation(summary = "Search an user by their public ID")
    public ResponseEntity<UserResponseDTO> findByPublicID(@PathVariable("id") String publicID) {
        UserResponseDTO response = userService.findByPublicID(publicID);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    @Operation(summary = "Create a new user")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody CreateLocalUserDTO request) {
        UserResponseDTO response = userService.storeAndMap(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update an user by their ID")
    public ResponseEntity<String> updateByID(@PathVariable(name = "id") UUID userID, @Valid @RequestBody UpdateLocalUserDTO request) {
        userService.updateByID(userID, request);
        return ResponseEntity.status(HttpStatus.OK).body("User successfully updated");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an user by their ID")
    public ResponseEntity<String> deleteByID(@PathVariable(name = "id") UUID userID) {
        userService.deleteByID(userID);
        return ResponseEntity.status(HttpStatus.OK).body("User successfully deleted");
    }
}
