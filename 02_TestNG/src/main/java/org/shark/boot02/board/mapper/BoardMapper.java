package org.shark.boot02.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.shark.boot02.board.dto.BoardDTO;

@Mapper
public interface BoardMapper {

  @Select(value = "SELECT NOW()")
  String now();
  
  @Select(value = "SELECT bid, title, content, created_at FROM tbl_board ORDER BY bid DESC")
  List<BoardDTO> selectBoardList();
  
  @Select(value = "SELECT bid, title, content, created_at FROM tbl_board WHERE bid = #{bid}")
  BoardDTO selectBoardById(@Param(value = "bid") Long bid);
  
  @Insert(value = "INSERT INTO tbl_board VALUES(null, #{title}, #{content}, NOW())")
  int insertBoard(BoardDTO board);
  
  @Update(value = "UPDATE tbl_board SET title = #{title}, content = #{content} WHERE bid = #{bid}")
  int updateBoard(BoardDTO board);
  
  @Delete(value = "DELETE FROM tbl_board WHERE bid = #{bid}")
  int deleteBoard(@Param(value = "bid") Long bid);
  
}
