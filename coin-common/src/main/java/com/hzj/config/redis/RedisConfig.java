package com.hzj.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // key序列化
        StringRedisSerializer serializer1 = new StringRedisSerializer();
        // value序列化
        GenericJackson2JsonRedisSerializer serializer2 = new GenericJackson2JsonRedisSerializer();

        redisTemplate.setKeySerializer(serializer1);
        redisTemplate.setValueSerializer(serializer2);

        redisTemplate.setHashKeySerializer(serializer1);
        redisTemplate.setHashValueSerializer(serializer2);
        return redisTemplate;
    }
}
