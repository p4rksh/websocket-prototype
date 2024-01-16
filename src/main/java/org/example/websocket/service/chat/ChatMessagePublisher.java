package org.example.websocket.service.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.websocket.controller.resource.MessageReq;
import org.example.websocket.controller.resource.NoticeMessageReq;
import org.example.websocket.controller.resource.TextMessageReq;
import org.example.websocket.protocol.MessageEvent;
import org.example.websocket.protocol.chat.NoticeMessageEvent;
import org.example.websocket.protocol.chat.TextMessageEvent;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
class ChatMessagePublisher extends ChatMessageProcessor {

    private final ChannelTopic channelTopic;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    @SuppressWarnings("unused")
    protected void write(MessageReq req) {
    }

    @Override
    protected void publish(MessageReq req) {
        // TODO: 발송자 유저 정보 넣어주는 로직 추가 필요
        var event = toEvent(req);
        log.info("publish text message: {}", event);
        redisTemplate.convertAndSend(channelTopic.getTopic(), event);
    }

    private MessageEvent toEvent(MessageReq req) {
        if (req instanceof TextMessageReq) {
            return TextMessageEvent.builder()
                    .roomId(req.getRoomId())
                    .senderId(req.getSenderId())
                    .senderName("p4rksh")
                    .senderImageUrl("img.png")
                    .type(req.getType())
                    .payload(req.getPayloadString())
                    .publishedAt(LocalDateTime.now())
                    .build();
        } else if (req instanceof NoticeMessageReq) {
            return NoticeMessageEvent.builder()
                    .roomId(req.getRoomId())
                    .type(req.getType())
                    .payload(req.getPayloadString())
                    .publishedAt(LocalDateTime.now())
                    .build();
        }

        throw new IllegalArgumentException("요청된 type에 해당하는 MessageReq가 없습니다. req = " + req.toString());
    }
}
