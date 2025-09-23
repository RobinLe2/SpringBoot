package org.shark.boot09.board.config;

import org.shark.boot09.board.dto.BoardDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class BoardRedisConfig {

  @Value("${spring.data.redis.host}")
  private String host;
  
  @Value("${spring.data.redis.port}")
  private int port;

  @Bean
  RedisConnectionFactory redisConnectionFactory() {
    return new LettuceConnectionFactory(host, port);
  }
  
  //--- 기초 테스트를 위한 RedisTemplate 빈 등록
  //    key, value 모두 String을 사용하는 방식
  @Bean
  RedisTemplate<String, String> redisTemplateString(){
    RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
    //---- Redis 연결을 위한 Factory 등록
    redisTemplate.setConnectionFactory(redisConnectionFactory());
    //---- Redis 통신을 위한 직렬화
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new StringRedisSerializer());
   
    return redisTemplate;
   
  }
  
  @Bean
  RedisTemplate<String, BoardDTO> redisTemplateBoard(){
    RedisTemplate<String, BoardDTO> redisTemplate = new RedisTemplate<>();
    //---- Redis 연결을 위한 Factory 등록
    redisTemplate.setConnectionFactory(redisConnectionFactory());
    //---- Redis 통신을 위한 직렬화
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    return redisTemplate;
  }
  
}
