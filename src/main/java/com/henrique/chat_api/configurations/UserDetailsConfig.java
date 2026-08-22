package com.henrique.chat_api.configurations;

import com.henrique.chat_api.entities.UserAccount;
import com.henrique.chat_api.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsConfig implements UserDetailsService {
    private final IUserRepository userRepository;

    @Override
    public UserAccount loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with " + username));
    }
}
