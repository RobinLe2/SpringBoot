package org.shark.boot09;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class ApplicationTests {
  
  //---- 스프링 부트에 의해 자동 설정되는 Redis Bean 목록 중
  //     StringRedisTemplate 빈이 존재하고 이 빈은 RedisTemplate<String, String>을 상속받은 형태
  //     public class StringRedisTemplate extends RedisTemplate<String, String> {}
  //     따라서 부트가 자동 설정한 StringResultTemplate과 사용자 정의 RedisTemplate<String, String>은 동일 타입이므로
  //     빈의 이름이 일치해야 주입이 가능합니다.
  
  @Autowired
  private RedisTemplate<String, String> redisTemplateString;  
  
  private final String key = "test:redis";
  private final String value = "In Memory NoSQL Database";
  
	@Test
	@DisplayName("Redis에 값 저장하기")
	void saveTest() {
	 ValueOperations<String, String> ops = redisTemplateString.opsForValue();
	 ops.set(key, value);  //---- 저장
	}
	
	 @Test
	 @DisplayName("Redis에 저장된 값 조회하기")
	 void findTest() {
	   ValueOperations<String, String> ops = redisTemplateString.opsForValue();
	   ops.set(key, value);  //---- 저장
	   Assertions.assertEquals(this.value, value);
	 }
	 
   @Test
   @DisplayName("Redis에 저장된 값 삭제하기")
   void deleteTest() {
     redisTemplateString.delete(key);
     ValueOperations<String, String> ops = redisTemplateString.opsForValue();
     String value = ops.get(key);  //---- 저장 
     Assertions.assertNull(value);
   }

   @Test
   @DisplayName("TTL 테스트하기")
   void ttlTest() {
     ValueOperations<String, String> ops = redisTemplateString.opsForValue();
     ops.set(key, value, 30, TimeUnit.SECONDS); // TTL 30초 설정
     try {
       Thread.sleep(5000);   // 5초 멈추기
     } catch (Exception e) {
       e.printStackTrace();
    }
    long remainTtl = redisTemplateString.getExpire(key);
    log.info("남은 TTL:{}", remainTtl);
   }
   
   }
