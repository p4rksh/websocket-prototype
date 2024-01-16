package org.example.websocket.repository.chat;

import org.example.websocket.entity.chat.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {
}
