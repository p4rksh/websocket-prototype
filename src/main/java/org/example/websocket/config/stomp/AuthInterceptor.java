package org.example.websocket.config.stomp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

@Slf4j
public class AuthInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);

        String command = String.valueOf(headerAccessor.getHeader("stompCommand"));

        if ("CONNECT".equals(command)) {
            log.info("connect success!");

            // TODO: Header에서 토큰값 추출하여 인증 처리
            // String token = String.valueOf(headerAccessor.getNativeHeader("Authorization"));
        }
        return message;
    }
}
