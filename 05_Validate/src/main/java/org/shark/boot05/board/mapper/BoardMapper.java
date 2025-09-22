package org.shark.boot05.board.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.shark.boot05.board.dto.BoardDTO;

@Mapper
public interface BoardMapper {
  int selectBoardCount();
  List<BoardDTO> selectBoardList(Map<String, Object> map);
  BoardDTO selectBoardById(Long bid);
  int insertBoard(BoardDTO board);
  int updateBoard(BoardDTO board);
  int deleteBoard(Long bid);
}