package com.henrique.chat_api.repositories;

import com.henrique.chat_api.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<UserAccount, UUID> {
    boolean existsByEmail(String email);
}
