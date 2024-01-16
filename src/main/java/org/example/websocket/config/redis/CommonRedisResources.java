package org.example.websocket.config.redis;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import org.example.websocket.util.Jacksons;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

@Configuration(proxyBeanMethods = false)
class CommonRedisResources {

    public static final String DEFAULT_JSON_REDIS_SERIALIZER = "jsonRedisSerializer";

    @Bean(name = DEFAULT_JSON_REDIS_SERIALIZER)
    GenericJackson2JsonRedisSerializer jsonRedisSerializer() {
        var mapper = Jacksons.getMapper();
        GenericJackson2JsonRedisSerializer.registerNullValueSerializer(mapper, null);
        mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(), DefaultTyping.NON_FINAL, As.PROPERTY);
        return new GenericJackson2JsonRedisSerializer(mapper);
    }

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean(ClientResources.class)
    DefaultClientResources defaultLettuceClientResources() {
        return DefaultClientResources.create();
    }

}
