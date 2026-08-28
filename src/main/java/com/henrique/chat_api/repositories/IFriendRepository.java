package com.henrique.chat_api.repositories;

import com.henrique.chat_api.entities.Friend;
import com.henrique.chat_api.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface IFriendRepository extends JpaRepository<Friend, Long> {
    Set<Friend> findAllByUserAccount(UserAccount userAccount);
}
