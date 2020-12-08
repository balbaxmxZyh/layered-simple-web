package com.newland.balbaxmx.layered.simple.redis.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @Author: zhangyh
 * @ClassName: RedisConfig
 * @Date: 2020/1/16 16:01
 * @Operation:
 * @Description: 开启redis缓存，springboot2X以上默认redis使用的是lettuce
 */
@Configuration
public class RedisConfig {

    @Autowired
    private LettuceConnectionFactory lettuceConnectionFactory;

    /**
     * 使用FastJsonRedisSerializer作为redis key与value序列化工具
     * @return
     */
    @Bean
    public FastJsonRedisSerializer<?> fastJsonRedisSerializer(){
        return new FastJsonRedisSerializer<>(Object.class);
    }

    /**
     * 注册RedisTemplate<String,Object>
     * @return
     */
    @Bean
    public RedisTemplate<String,Object> redisTemplate(){
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory);
        template.setValueSerializer(fastJsonRedisSerializer());
        template.setKeySerializer(fastJsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
}
