package com.henrique.chat_api.user_account.unit;

import com.henrique.chat_api.dtos.user.CreateLocalUserDTO;
import com.henrique.chat_api.entities.UserAccount;
import com.henrique.chat_api.entities.UserRole;
import com.henrique.chat_api.enums.AccountProviders;
import com.henrique.chat_api.enums.Roles;
import com.henrique.chat_api.repositories.IUserRepository;
import com.henrique.chat_api.services.UserService;
import jakarta.validation.Validation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private IUserRepository userRepository;
    @Mock
    private static PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private static final String hashedPassword = "hashedPassword";
    private static final String publicID = "123456";

    @BeforeAll
    public static void mockUserAccountBuilder() {
        try (MockedStatic<UserAccount> mocked = Mockito.mockStatic(UserAccount.class)) {
            Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn(hashedPassword);
            mocked.when(UserAccount::generatePublicID).thenReturn(publicID);
            mocked.when(UserAccount::builder).thenCallRealMethod();
        }
    }

    @Test
    public void shouldStoreANewUser() {
            CreateLocalUserDTO request = new CreateLocalUserDTO("User", "user@gmail.com", "12345678");
            Mockito.when(userRepository.existsByEmail(Mockito.anyString())).thenReturn(false);

            UserAccount expected = buildUser(request);
            UserAccount result = userService.store(request);

            Assertions.assertThat(result)
                    .usingRecursiveComparison()
                    .ignoringFields("id", "createdAt", "roles.id", "roles.userAccount")
                    .isEqualTo(expected);
    }

    public UserAccount buildUser(CreateLocalUserDTO request) {
        UserAccount user = UserAccount.builder()
                .name(request.username())
                .email(request.email())
                .passwordHash(hashedPassword)
                .publicID(publicID)
                .provider(AccountProviders.LOCAL)
                .build();
        UserRole roles = UserRole.builder()
                .role(Roles.USER)
                .userAccount(user)
                .build();
        user.setRoles(Set.of(roles));

        return user;
    }
}
