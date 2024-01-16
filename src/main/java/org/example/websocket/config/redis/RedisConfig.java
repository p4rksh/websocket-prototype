package org.example.websocket.config.redis;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import io.lettuce.core.TimeoutOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration(proxyBeanMethods = false)
@Import(CommonRedisResources.class)
public class RedisConfig {

    @Bean
    LettuceConnectionFactory lettuceConnectionFactory(RedisProperties properties) {
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .clientOptions(createClientOptions(properties))
                .clientName(properties.getClientName())
                .build();

        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(properties.getHost(), properties.getPort()), clientConfig);
    }

    private static ClientOptions createClientOptions(RedisProperties properties) {
        return ClientOptions.builder()
                .socketOptions(SocketOptions.builder()
                        .connectTimeout(properties.getConnectTimeout())
                        .build())
                .timeoutOptions(TimeoutOptions.enabled())
                .build();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory connectionFactory,
                                                       @Qualifier(CommonRedisResources.DEFAULT_JSON_REDIS_SERIALIZER) GenericJackson2JsonRedisSerializer jsonRedisSerializer) {
        var redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setDefaultSerializer(StringRedisSerializer.UTF_8);
        redisTemplate.setStringSerializer(StringRedisSerializer.UTF_8);
        redisTemplate.setKeySerializer(StringRedisSerializer.UTF_8);
        redisTemplate.setHashKeySerializer(StringRedisSerializer.UTF_8);
        redisTemplate.setValueSerializer(jsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);
        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(LettuceConnectionFactory connectionFactory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(connectionFactory);
        return stringRedisTemplate;
    }

}
