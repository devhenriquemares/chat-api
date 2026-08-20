package com.henrique.chat_api.services;

import com.henrique.chat_api.dtos.user.CreateLocalUserDTO;
import com.henrique.chat_api.dtos.user.UserResponse;
import com.henrique.chat_api.entities.UserAccount;
import com.henrique.chat_api.enums.AccountProviders;
import com.henrique.chat_api.mappers.UserMapper;
import com.henrique.chat_api.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse store(CreateLocalUserDTO request) {
        String hashedPassword = passwordEncoder.encode(request.password());

        UserAccount user = UserAccount.builder()
                .username(request.username())
                .email(request.email())
                .passwordHash(hashedPassword)
                .provider(AccountProviders.LOCAL)
                .publicID(UserAccount.generatePublicID())
                .build();

        userRepository.save(user);
        return UserMapper.toResponse(user);
    }

    public void findByID() {

    }

    public void updateByID() {

    }

    public void deleteByID() {

    }
}
