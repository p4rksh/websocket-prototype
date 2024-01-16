package org.example.websocket.config.redis;

import lombok.RequiredArgsConstructor;
import org.example.websocket.service.chat.RedisChatMessageSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration(proxyBeanMethods = false)
@Import(RedisConfig.class)
@RequiredArgsConstructor
public class RedisListenerConfig {

    private final RedisChatMessageSubscriber redisChatMessageSubscriber;

    @Bean
    public ChannelTopic channelTopic() {
        return new ChannelTopic("chat");
    }

    @Bean
    public MessageListenerAdapter listenerAdapter() {
        return new MessageListenerAdapter(redisChatMessageSubscriber, "onMessage");
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(LettuceConnectionFactory connectionFactory,
                                                                       MessageListenerAdapter listenerAdapter,
                                                                       ChannelTopic channelTopic) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, channelTopic);
        return container;
    }

}
