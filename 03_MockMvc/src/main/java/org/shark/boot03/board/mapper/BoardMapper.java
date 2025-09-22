package org.shark.boot03.board.mapper;

import java.util.List;

// import org.apache.ibatis.annotations.Mapper;
import org.shark.boot03.board.dto.BoardDTO;

/*
 * Mapper Interface와 XML Mapper 활용 규칙
 * 
 * 1. XML의 namespace는 반드시 Mapper Interface의 전체 클래스명과 일치해야 합니다.
 * 2. XML의 SQL id는 반드시 Mapper Interface의 메소드명과 일치해야 합니다.
 */

// @Mapper
public interface BoardMapper {
  List<BoardDTO> selectBoardList();
  BoardDTO selectBoardById(Long bid);
  int insertBoard(BoardDTO board);
  int updateBoard(BoardDTO board);
  int deleteBoard(Long bid);
}