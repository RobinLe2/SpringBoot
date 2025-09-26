package org.shark.boot12;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.shark.boot12.common.util.JpaUtil;
import org.shark.boot12.user.entity.AccessLog;
import org.shark.boot12.user.entity.User;
import org.shark.boot12.user.enums.Gender;
import org.shark.boot12.user.enums.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class CRUDTests {

  @Autowired
  private JpaUtil jpaUtil;
  
  private EntityManager em;
  
  //----- 전체 테스트 시작 전 : 엔티티 매니저 팩토리 생성
  @BeforeAll
  void setUpBeforeClass() {
    jpaUtil.initFactory();
  }
  
  //----- 각 테스트 시작 전 : 엔티티 매니저 생성
  @BeforeEach
  void setUp() {
    em = jpaUtil.getEntityManager();
  }
  
  //----- 각 테스트 종료 후 : 엔티티 매니저 소멸
  @AfterEach
  void tearDown() {
    if (em != null && em.isOpen()) {
      em.close();
    }
  }
  
  //----- 전체 테스트 종료 후 : 엔티티 매니저 팩토리 소멸
  @AfterAll
  void tearDownAfterClass() {
    jpaUtil.closeFactory();
  }
  
  @Test
  @DisplayName("User 엔티티 등록하기")
  void createUserTest() {
     
    // User 엔티티 생성
    User user = User.createUser("홍길동", "hong@example.com", Gender.MALE);
    
    // 트랜잭션 시작
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    try {
      
      // 엔티티를 영속성 컨텍스트(Persistence Context)에 저장
      em.persist(user);
      log.info("after persist()");
      
      // 트랜잭션 커밋
      tx.commit();
      log.info("after commit()");
      
    } catch (Exception e) {
      
      // 트랜잭션 롤백
      tx.rollback();
      throw e;
      
    }
    
    assertNotNull(user.getId());
    assertNotNull(user.getCreatedAt());
    
    /*
     * 실행 순서 정리
     * 1. insert into
     * 2. after persist()
     * 3. after commit()
     * 
     * 실행 순서 이유
     * 영속성 컨텍스트에 엔티티를 저장하기 위해서는 반드시 엔티티에 ID가 필요합니다.
     * User 엔티티는 AUTO INCREMENT 전략을 사용하므로, INSERT 쿼리를 실행해야만 ID를 알 수 있습니다.
     * 따라서, 영속성 컨텍스트에 엔티티를 저장하는 persist() 호출 시 곧바로 DB INSERT 쿼리가 실행됩니다.
     */

  }
  
  @Test
  @DisplayName("AccessLog 엔티티 등록하기")
  void createAccessLogTest() {
    
    // AccessLog 엔티티 생성
    AccessLog accessLog = AccessLog.createAccessLog("USER_LOGIN", "사용자 로그인 성공", LogLevel.INFO, "192.168.100.5", "Mozilla/5.0");
    
    // 트랜잭션 시작
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    
    try {
      
      // AccessLog 엔티티를 영속성 컨텍스트에 저장
      em.persist(accessLog);
      log.info("after persist()");
      
      // 트랜잭션 커밋
      tx.commit();
      log.info("after commit()");
      
    } catch (Exception e) {
      
      // 트랜잭션 롤백
      tx.rollback();
      throw e;
      
    }
    
    assertNotNull(accessLog.getId());
    assertNotNull(accessLog.getCreatedAt());
    
    /*
     * 실행 순서 정리
     * 1. after persist()
     * 2. insert into
     * 3. after commit()
     * 
     * 실행 순서 이유
     * 영속성 컨텍스트에 엔티티를 저장하기 위해서는 반드시 엔티티에 ID가 필요합니다.
     * AccessLog 엔티티는 access_log_seq 테이블로부터 ID를 받아서 사용합니다.
     * 따라서, AccessLog 엔티티를 DB에 INSERT 하지 않아도 엔티티의 ID를 알 수 있으므로 persist() 메소드 호출 시 INSERT 쿼리가 동작하지 않습니다.
     */
    
  }
  
  @Test
  @DisplayName("User 엔티티 조회하기")
  void findUserTest( ) {
    
    // 엔티티를 조회할 땐 ID를 이용해서 조회합니다.
    
    Long id = 1L;
    
    //----- 엔티티 매니저를 이용한 엔티티 조회 원리
    //   1. find() 호출 시 영속성 컨텍스트에서 엔티티를 조회합니다.
    //   2. 없으면 DB에서 조회합니다.
    //   3. 그래도 없으면 null 반환합니다.
    //   4. 조회한 엔티티는 영속성 컨텍스트에서 관리합니다. (find() 호출 결과 엔티티는 영속성 컨텍스트에 저장됩니다.)
    User foundUser = em.find(User.class, id);
        
    assertNotNull(foundUser);
    
  }
  
  @Test
  @DisplayName("AccessLog 엔티티 조회하기")
  void findAccessLogTest() {
    Long id = 1L;
    AccessLog accessLog = em.find(AccessLog.class, id);
    assertNotNull(accessLog);
  }
  
  
  @Test
  @DisplayName("User 엔티티 삭제하기")
  void deleteUserTest() {
    
    Long id = 1L;
    
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    
    try {
      
      // 삭제할 엔티티를 조회해서 영속성 컨텍스트에 저장합니다.
      User foundUser = em.find(User.class, id);
      
      // 삭제 예정 상태로 엔티티를 변경합니다.
      em.remove(foundUser);
      
      // 실제로 삭제합니다.
      tx.commit();
      
    } catch (Exception e) {
      
      tx.rollback();
      throw e;
      
    }
    
    assertNull(em.find(User.class, id));
    
  }
  
  @Test
  @DisplayName("AccessLog 엔티티 삭제하기")
  void deleteAccessLogTest() {
      
    Long id = 1L;
    
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    
    try {
      
      AccessLog foundAccessLog 
        = em.find(AccessLog.class, id);  // DB에서 AccessLog 엔티티를 조회해 영속성 컨텍스트에 저장합니다. 
      em.remove(foundAccessLog);  //------- AccessLog 엔티티를 영속성 컨텍스트에서 제거하고 삭제 상태로 바꿉니다.
      em.persist(foundAccessLog);  //------ 삭제 상태의 AccessLog 엔티티를 영속성 컨텍스트에 다시 저장합니다.
      
      tx.commit();  //--------------------- 영속성 컨텍스트에 여전히 AccessLog가 저장되어 있으므로 아무런 변화가 없습니다.
      
    } catch (Exception e) {
      tx.rollback();
      throw e;
    }
    
    assertNull(em.find(AccessLog.class, id));
    
  }
  
  @Test
  @DisplayName("User 엔티티를 수정하기")
  void updateUserTest() {
    
    Long id = 1L;
    String username = "홍길순";
    Gender gender = Gender.FEMALE;
    
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    
    try {
      
      // 수정할 엔티티를 조회해서 영속성 컨텍스트에 저장합니다.
      User foundUser = em.find(User.class, id);
      
      // 엔티티를 수정합니다.
      foundUser.setUsername(username);
      foundUser.setGender(gender);
      
      // 트랜잭션 커밋
      tx.commit();
      
    } catch (Exception e) {
      tx.rollback();
      throw e;
    }
    
    assertEquals(username, em.find(User.class, id).getUsername());
    
  }
  
  
  
  
  
}








