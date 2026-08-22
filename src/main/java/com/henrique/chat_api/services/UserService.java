package com.henrique.chat_api.services;

import com.henrique.chat_api.dtos.user.CreateLocalUserDTO;
import com.henrique.chat_api.dtos.user.UpdateLocalUserDTO;
import com.henrique.chat_api.dtos.user.UserResponseDTO;
import com.henrique.chat_api.entities.UserAccount;
import com.henrique.chat_api.entities.UserRole;
import com.henrique.chat_api.enums.AccountProviders;
import com.henrique.chat_api.enums.Roles;
import com.henrique.chat_api.exceptions.EmailAlreadyExistsException;
import com.henrique.chat_api.exceptions.InvalidPasswordException;
import com.henrique.chat_api.exceptions.OldPasswordRequiredException;
import com.henrique.chat_api.exceptions.UserNotFoundException;
import com.henrique.chat_api.mappers.UserMapper;
import com.henrique.chat_api.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserAccount store(CreateLocalUserDTO request) {
        if(userRepository.existsByEmail(request.email())) throw new EmailAlreadyExistsException();

        String hashedPassword = passwordEncoder.encode(request.password());
        UserAccount user = UserAccount.builder()
                .name(request.username())
                .email(request.email())
                .passwordHash(hashedPassword)
                .provider(AccountProviders.LOCAL)
                .publicID(UserAccount.generatePublicID())
                .build();

        UserRole defaultRole = UserRole.builder()
                .role(Roles.USER)
                .userAccount(user)
                .build();

        user.setRoles(Set.of(defaultRole));

        userRepository.save(user);
        return user;
    }

    public UserResponseDTO storeAndMap(CreateLocalUserDTO request) {
        UserAccount user = store(request);
        return UserMapper.toResponse(user);
    }

    public UserResponseDTO findByID(UUID userID) {
        UserAccount user = userRepository.findById(userID)
                .orElseThrow(() -> new UserNotFoundException(userID));

        return UserMapper.toResponse(user);
    }

    public void updateByID(UUID userID, UpdateLocalUserDTO request) {
        UserAccount user = userRepository.findById(userID)
                .orElseThrow(() -> new UserNotFoundException(userID));

        if (request.username() != null) user.setName(request.username());
        if (request.email() != null) user.setEmail(request.email());
        if (request.newPassword() != null) {
            if (request.oldPassword() == null) throw new OldPasswordRequiredException();
            if (!passwordEncoder.matches(request.oldPassword(), user.getPasswordHash())) throw new InvalidPasswordException();

            user.setPasswordHash(passwordEncoder.encode(request.newPassword()));
        }

        userRepository.save(user);
    }

    public void deleteByID(UUID userID) {
        UserAccount user = userRepository.findById(userID)
                .orElseThrow(() -> new UserNotFoundException(userID));

        userRepository.delete(user);
    }
}
