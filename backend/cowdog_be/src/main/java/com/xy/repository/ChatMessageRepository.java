package com.xy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xy.entity.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>{
}
