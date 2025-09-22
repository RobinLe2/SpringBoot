package org.shark.boot01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.shark.boot01.board.dto.BoardDTO;
import org.shark.boot01.board.mapper.BoardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/*
 * @SpringBootTest
 * 
 * 1. Spring Boot 기반 테스트를 실행할 때 사용하는 어노테이션입니다.
 * 2. 일반적으로 사용하는 @ContextConfiguration보다 더 많은 기능을 제공합니다.
 * 3. 모든 Bean을 실제로 로드하여 완전한 통합 테스트 환경을 제공합니다.
 */

@SpringBootTest
class ApplicationTests {

  @Autowired
  private BoardMapper boardMapper;
  
  private final static Logger log = LoggerFactory.getLogger(ApplicationTests.class);
  
	@Test
	@DisplayName("컨텍스트 로드 테스트")
	void contextLoads() {
	  // 컨텍스트가 정상적으로 로드되는지 확인
	  assertNotNull(boardMapper);
	  log.info("컨텍스트 로드 테스트 종료");
	}
	
	@Test
	@DisplayName("현재 시간 가져오기 테스트")
	void nowTest() {
	  log.info("NOW: {}", boardMapper.now());
	}
	
	@Test
	@DisplayName("게시판 목록 가져오기 테스트")
	void boardListTest() {
	  assertEquals(1, boardMapper.selectBoardList().size());
	}
	
	@Test
	@DisplayName("게시판 상세 테스트")
	void boardDetailTest() {
	  Long bid = 1L;
	  assertEquals("테스트 제목", boardMapper.selectBoardById(bid).getTitle());
	}
	
	@Test
	@DisplayName("신규 게시글 등록 테스트")
	void boardInsertTest() {
	  BoardDTO board = new BoardDTO(null, "신규 제목", "신규 내용", null);
	  assertEquals(1, boardMapper.insertBoard(board));
	  assertEquals("신규 제목", boardMapper.selectBoardById(2L).getTitle());
	  log.info("등록된 신규 게시글: {}", boardMapper.selectBoardById(2L).toString());
	  // log.info("등록된 created_at: {}", boardMapper.selectBoardById(2L).getCreatedAt());
	}
	
	@Test
	@DisplayName("게시글 수정 테스트")
	void boardUpdateTest() {
	  Long bid = 1L;
	  String title = "수정 제목";
	  boardMapper.updateBoard(new BoardDTO(bid, title, null, null));
	  assertEquals(title, boardMapper.selectBoardById(bid).getTitle());
	  assertNull(boardMapper.selectBoardById(bid).getContent());
	}
	
	@Test
	@DisplayName("게시글 삭제 테스트")
	void boardDeleteTest() {
	  Long bid = 1L;
	  boardMapper.deleteBoard(bid);
	  assertNull(boardMapper.selectBoardById(bid));
	}

}
