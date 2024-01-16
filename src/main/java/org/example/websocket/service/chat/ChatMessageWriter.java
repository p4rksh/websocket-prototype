package org.example.websocket.service.chat;

import lombok.RequiredArgsConstructor;
import org.example.websocket.controller.resource.MessageReq;
import org.example.websocket.entity.chat.ChatMessageEntity;
import org.example.websocket.repository.chat.ChatMessageRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
class ChatMessageWriter extends ChatMessageProcessor {

    private final ChatMessageRepository repository;

    @Override
    @Transactional
    protected void write(MessageReq req) {
        var entity = ChatMessageEntity.builder()
                .roomId(Long.parseLong(req.getRoomId()))
                .userId(Long.parseLong(req.getSenderId()))
                .type(req.getType())
                .payload(req.getPayloadString())
                .build();
        repository.save(entity);
    }

    @Override
    @SuppressWarnings("unused")
    protected void publish(MessageReq req) {
    }
}
