package org.shark.boot03.board.service;

import java.util.List;

import org.shark.boot03.board.dto.BoardDTO;
import org.shark.boot03.board.mapper.BoardMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

  private final BoardMapper boardMapper;

  @Override
  public List<BoardDTO> getBoardList() {
    return boardMapper.selectBoardList();
  }

  @Override
  public BoardDTO getBoardById(Long bid) {
    return boardMapper.selectBoardById(bid);
  }

  @Override
  public Boolean createBoard(BoardDTO board) {
    return boardMapper.insertBoard(board) > 0;
  }

  @Override
  public Boolean updateBoard(BoardDTO board) {
    return boardMapper.updateBoard(board) > 0;
  }

  @Override
  public Boolean deleteBoard(Long bid) {
    return boardMapper.deleteBoard(bid) > 0;
  }

}
