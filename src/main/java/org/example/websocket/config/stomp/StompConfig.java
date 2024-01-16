package org.example.websocket.config.stomp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class StompConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 웹소켓 연결 Endpoint 지정
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("https://jxy.me/")   // 테스트 tool
                .withSockJS();
    }

    /**
     * message들을 publish, subscribe 할 때 Endpoint Root Path 지정
     * @param config
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes("/pub");
        config.enableSimpleBroker("/sub");
    }

    @Bean
    public ChannelInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    /**
     * 웹소켓 서버와 통신할 때 메세지가 서버에 전달되기 이전 시점에서 intercept 하여 토큰 인증을 하게끔 Interceptor 등록
     * @param registration
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
         registration.interceptors(authInterceptor());
    }

}
