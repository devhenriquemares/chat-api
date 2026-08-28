package com.henrique.chat_api.repositories;

import com.henrique.chat_api.entities.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFriendRepository extends JpaRepository<Friend, Long> {
}
