package com.henrique.chat_api.repositories;

import com.henrique.chat_api.entities.FriendRequest;
import com.henrique.chat_api.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface IFriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    Set<FriendRequest> findAllByRecipient(UserAccount recipient);
}
