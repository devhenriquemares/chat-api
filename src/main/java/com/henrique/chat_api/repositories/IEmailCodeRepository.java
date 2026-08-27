package com.henrique.chat_api.repositories;

import com.henrique.chat_api.entities.EmailCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IEmailCodeRepository extends JpaRepository<EmailCode, Long> {
    Optional<EmailCode> findByCode(String code);
}
