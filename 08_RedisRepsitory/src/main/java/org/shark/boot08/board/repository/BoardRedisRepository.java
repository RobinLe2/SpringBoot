package org.shark.boot08.board.repository;

import org.shark.boot08.board.entity.Board;

import org.springframework.data.repository.CrudRepository;

public interface BoardRedisRepository extends CrudRepository<Board, Long> {
 /* CrudRepository<BoardDTO, Long>
  *  BoardDTO를 이용한 CRUD 작업을 진행합니다.
  *  BoardDTO의 ID(식별자)는 Long 타입입니다. 
  */
}
