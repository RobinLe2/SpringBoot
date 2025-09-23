package org.shark.boot09.board.serivce;

import org.shark.boot09.board.dto.BoardDTO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardRedisService {

   private final RedisTemplate<String, BoardDTO> redisTemplate;
   
   /*
    * key 형식
    *   board:1
    *   board:2
    *   ...
    *   --------
    *   board:{bid}
    * 
    */
   
    String PREFIX = "board: ";
   //---- 저장
   public void save(BoardDTO boardDTO) {
     ValueOperations<String, BoardDTO> ops = redisTemplate.opsForValue();
     ops.set(PREFIX + boardDTO.getBid(), boardDTO);
   }
   
   //---- 조회 (bid를 받아서 해당하는 BoardDTO 객체 반환)
   public BoardDTO findById(Long bid) {
     ValueOperations<String, BoardDTO> ops = redisTemplate.opsForValue();
     return ops.get(PREFIX + bid);
   }
  //--- 삭제 (bid를 받아서 해당하는 BoardDTO 삭제)
   public void  deleteById(Long bid) {
     redisTemplate.delete(PREFIX + bid);
   }
   
   }

