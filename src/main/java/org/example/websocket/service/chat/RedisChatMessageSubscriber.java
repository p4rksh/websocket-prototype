package org.example.websocket.service.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.websocket.exception.ChatMessageDeserializeException;
import org.example.websocket.protocol.MessageEvent;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisChatMessageSubscriber implements MessageListener {
    private final RedisTemplate<String, Object> redisTemplate;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            var event = (MessageEvent) redisTemplate.getValueSerializer().deserialize(message.getBody());
            log.info("Consume Event: {}", event);

            if (event != null) {
                messagingTemplate.convertAndSend("/sub/chat/room/" + event.getContext(), event);
            }
        } catch (Exception e) {
            throw new ChatMessageDeserializeException("Error occurred while deserialize redis event. event= " + new String(message.getBody()));
        }
    }
}
