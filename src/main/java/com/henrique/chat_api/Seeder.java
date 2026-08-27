package com.henrique.chat_api;

import com.henrique.chat_api.entities.UserAccount;
import com.henrique.chat_api.entities.UserRole;
import com.henrique.chat_api.enums.AccountProviders;
import com.henrique.chat_api.enums.Roles;
import com.henrique.chat_api.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class Seeder implements CommandLineRunner {
    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            log.info("Initializing admin seed");
            if (adminEmail == null || adminPassword == null) {
                throw new Exception("Admin credentials are empty");
            }

            String passwordHash = passwordEncoder.encode(adminPassword);
            UserAccount admin = UserAccount.builder()
                    .name("admin")
                    .email(adminEmail)
                    .passwordHash(passwordHash)
                    .isVerified(true)
                    .provider(AccountProviders.LOCAL)
                    .publicID(UserAccount.generatePublicID())
                    .build();

            Set<UserRole> adminRoles = Arrays.stream(Roles.values())
                    .map(role -> UserRole.builder().role(role).userAccount(admin).build())
                    .collect(Collectors.toSet());

            admin.setRoles(adminRoles);

            userRepository.save(admin);
            log.info("Admin successfully seeded");
        }
    }
}
