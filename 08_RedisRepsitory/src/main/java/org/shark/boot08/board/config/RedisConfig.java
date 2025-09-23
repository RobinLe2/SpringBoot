package org.shark.boot08.board.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

public class RedisConfig {

    @Value("${spring.data.redis.host}")  //------ spring.data.redis.host 프로퍼티 읽기
    private String host;
    
    @Value("${spring.data.redis.port}")  //------ spring.data.redis.port 프로퍼티 읽기
    private int port;
    
    
    @Bean
    RedisConnectionFactory redisConnectionFactory() {
      return new LettuceConnectionFactory(host, port);
    }
    
}
