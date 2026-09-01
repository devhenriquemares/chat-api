package com.henrique.chat_api.repositories;

import com.henrique.chat_api.entities.Friend;
import com.henrique.chat_api.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface IFriendRepository extends JpaRepository<Friend, Long> {
    Set<Friend> findAllByUserAccount(UserAccount userAccount);

    @Query("SELECT f FROM Friend f WHERE f.friendAccount.id = :friendID")
    Optional<Friend> findByFriendID(@Param("friendID")UUID friendID);
}
