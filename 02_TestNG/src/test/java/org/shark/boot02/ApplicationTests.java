package org.shark.boot02;

import static org.testng.Assert.*;

import org.shark.boot02.board.dto.BoardDTO;
import org.shark.boot02.board.mapper.BoardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

/*
 * TestNG
 * 
 * 1. TestNG는 Java 기반의 강력한 테스트 프레임워크입니다.
 * 2. JUnit의 대안으로 사용되며 더 풍부한 기능과 유연성을 제공합니다.
 * 3. 주요 기능
 *   1) 강력한 애노테이션 시스템: 더 세밀한 테스트 제어 가능
 *   2) 의존성 테스트: 테스트 간 의존성 설정 가능
 *   3) 병렬 실행: 멀티스레드 테스트 지원
 * 4. 필요한 디펜던시
 *   1) spring-boot-starter-test : @SpringBootTest 지원
 *   2) testng                   : TestNG 핵심 기능 지원(@Test 등)
 *   3) spring-test              : AbstractTestNGSpringContextTests 클래스
 */

// TestNG의 원활한 활용하기 위해서 [Help] - [Eclipse Marketplace] - "TestNG for Eclipse" 조회 후 설치를 할 수 있습니다.

// 이번 테스트는 수월한 테스트를 위해서 schema.sql을 직접 한 번만 실행합니다. (schema.sql 파일을 매번 자동 실행하는 설정을 제거해야 합니다.)

@SpringBootTest
class ApplicationTests extends AbstractTestNGSpringContextTests {

  // @Autowired를 사용하기 위해서는 AbstractTestNGSpringContextTests 클래스를 상속해야 합니다.
  // AbstractTestNGSpringContextTests 클래스를 상속하면 Spring Context 설정이 추가되어 Spring Container 안의 빈(Bean)을 사용할 수 있습니다.
  
  @Autowired
  private BoardMapper boardMapper;
  
  private final static Logger log = LoggerFactory.getLogger(ApplicationTests.class);

  @Test(description = "컨텍스트 로드 테스트, nowTest()를 테스트하면 contextLoads()는 함께 테스트됩니다.")
  public void contextLoads() {
    assertNotNull(boardMapper);
    log.info("컨텍스트 로드 테스트 종료");
  }

  @Test(description = "현재 시간 가져오기 테스트",
        dependsOnMethods = {"contextLoads"})  // 테스트 간 의존 설정 : contextLoads() 메소드가 테스트 성공하면 nowTest() 메소드도 테스트를 합니다.
  public void nowTest() {
    log.info("NOW: {}", boardMapper.now());
  }

  @Test(priority = 1, description = "신규 게시글 등록 테스트")
  public void boardInsertTest() {
    BoardDTO board = new BoardDTO(null, "신규 제목", "신규 내용", null);
    assertEquals(boardMapper.insertBoard(board), 1);  // 인자 순서가 JUnit5와 반대로 설정되어 있습니다. 
  }
  
  @Test(priority = 2, description = "게시판 상세 테스트")
  public void boardDetailTest() {
    assertEquals(boardMapper.selectBoardById(1L).getTitle(), "신규 제목");    
  }

  @Test(priority = 3, description = "게시판 목록 가져오기 테스트")
  public void boardListTest() {
    assertEquals(boardMapper.selectBoardList().size(), 1);
  }

  @Test(priority = 4, description = "게시글 수정 테스트")
  public void boardUpdateTest() {
    Long bid = 1L;
    String title = "수정 제목";
    boardMapper.updateBoard(new BoardDTO(bid, title, null, null));
    assertEquals(title, boardMapper.selectBoardById(bid).getTitle());
    assertNull(boardMapper.selectBoardById(bid).getContent());
  }

  @Test(priority = 5, description = "게시글 삭제 테스트")
  public void boardDeleteTest() {
    Long bid = 1L;
    boardMapper.deleteBoard(bid);
    assertNull(boardMapper.selectBoardById(bid));
  }
  
}
