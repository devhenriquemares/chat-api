package com.henrique.chat_api.repositories;

import com.henrique.chat_api.entities.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFriendRequestRepository extends JpaRepository<FriendRequest, Long> {
}
