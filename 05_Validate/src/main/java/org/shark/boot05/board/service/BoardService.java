package org.shark.boot05.board.service;

import java.util.Map;

import org.shark.boot05.board.dto.BoardDTO;
import org.shark.boot05.common.dto.PageDTO;

public interface BoardService {
  Map<String, Object> getBoardList(PageDTO dto, String sort);
  BoardDTO getBoardById(Long bid);
  Boolean createBoard(BoardDTO board);
  Boolean updateBoard(BoardDTO board);
  Boolean deleteBoard(Long bid);
}
