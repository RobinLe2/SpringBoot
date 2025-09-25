package org.shark.boot12;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.shark.boot12.common.util.JpaUtil;
import org.shark.boot12.user.entity.User;
import org.shark.boot12.user.enums.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;


@TestInstance(Lifecycle.PER_CLASS)
@Slf4j
@SpringBootTest
class CRUDTests {

  @Autowired
  private JpaUtil jpaUtil;
  
  private EntityManager em;
  
  //----- 전체 테스트 시작 전 : 엔티티 매니저 팩토리 생성
  @BeforeAll
  void setUpBeforeClass() {
    jpaUtil.initFactory();
  }
  
  
  //---- 각 테스트 시작 전 : 엔티티 메니저 생성
  @BeforeEach
  void setup() {
    em = jpaUtil.getEntityManager();

  }
  
  
  //---- 각 테스트 종료 후 : 엔티티 매니저 소멸
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
    
    //User 엔티티 생성
    User user = User.createUser("홍길동", "hong@example.com", Gender.MALE );
    
    // 트랜잭션 시작
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    
    try {
    // 엔티티를 영속성 컨텍스트 (Persistence Context)에 저장
    em.persist(user);
    log.info("after persist()");
     
    // 트랜잭션 커밋
    tx.commit();
    log.info("after commit()");
   
    } catch (Exception e) {
      // TODO: handle exception
      tx.rollback();
      throw e;
    }
   
    Assertions.assertNotNull(user.getId());
    Assertions.assertNotNull(user.getCreatedAt());
    
  }
  
  

  


}
