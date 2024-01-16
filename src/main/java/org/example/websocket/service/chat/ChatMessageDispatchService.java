package org.example.websocket.service.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.websocket.controller.resource.MessageReq;
import org.example.websocket.controller.resource.MessageType;
import org.example.websocket.entity.chat.ChatMessageEntity;
import org.example.websocket.protocol.MessageEvent;
import org.example.websocket.protocol.chat.NoticeMessageEvent;
import org.example.websocket.protocol.chat.TextMessageEvent;
import org.example.websocket.repository.chat.ChatMessageRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatMessageDispatchService extends ChatMessageDispatcher {

    private final ChatMessageRepository repository;
    private final ChannelTopic channelTopic;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    @Transactional
    protected ChatMessageEntity write(MessageReq req) {
        var entity = ChatMessageEntity.builder()
                .roomId(Long.parseLong(req.getRoomId()))
                .userId(Long.parseLong(req.getSenderId()))
                .type(req.getType())
                .payload(req.getPayloadString())
                .build();
        return repository.save(entity);
    }

    @Override
    protected void publish(ChatMessageEntity entity) {
        var event = toEvent(entity);
        log.info("publish text message: {}", event);
        redisTemplate.convertAndSend(channelTopic.getTopic(), event);
    }

    private MessageEvent toEvent(ChatMessageEntity entity) {
        // TODO: 발송자 유저 정보 넣어주는 로직 추가 필요
        if (MessageType.TEXT == entity.getType()) {
            return TextMessageEvent.builder()
                    .id(entity.getId().toString())
                    .roomId(entity.getRoomId().toString())
                    .senderId(entity.getUserId().toString())
//                    .senderName("p4rksh")
//                    .senderImageUrl("img.png")
                    .type(entity.getType())
                    .payload(entity.getPayload())
                    .publishedAt(LocalDateTime.now())
                    .build();
        } else if (MessageType.NOTICE == entity.getType()) {
            return NoticeMessageEvent.builder()
                    .id(entity.getId().toString())
                    .roomId(entity.getRoomId().toString())
                    .type(entity.getType())
                    .payload(entity.getPayload())
                    .publishedAt(LocalDateTime.now())
                    .build();
        }

        throw new IllegalArgumentException("요청된 type에 해당하는 MessageReq가 없습니다. entity = " + entity);
    }
}
