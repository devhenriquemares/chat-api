package com.henrique.chat_api.repositories;

import com.henrique.chat_api.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IMessageRepository extends JpaRepository<Message, UUID> {

}
