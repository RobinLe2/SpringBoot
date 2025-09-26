package org.shark.boot12;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class LifeCycleTests {

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
  @DisplayName("비영속 엔티티 점검")
  void transientTest() {
     
    // User 엔티티 생성
    User user = User.createUser("홍길동", "hong@example.com", Gender.MALE);
    
    // 영속성 컨텍스트에 저장되어 있는지 확인
    assertTrue(em.contains(user));

  }

  @Test
  @DisplayName("영속 엔티티 점검")
  void managedTest() {
    
    //----- 영속 엔티티(Managed Entity)가 되는 방법
    //  1. persist()
    //  2. find()
    //  3. JPQL
    //  4. merge()
    
    // User 엔티티 생성
    User user = User.createUser("홍길동", "hong@example.com", Gender.MALE);
    
    // persist()
    em.persist(user);
    
    // AccessLog 엔티티 조회 : find()
    AccessLog accessLog = em.find(AccessLog.class, 1L);
    
    // 영속성 컨텍스트에 저장되어 있는지 확인
    assertTrue(em.contains(user));
    assertTrue(em.contains(accessLog));
    
  }
  
  @Test
  @DisplayName("준영속 엔티티 detach() 테스트")
  void detachTest() {
    
    //----- 준영속 엔티티
    //  1. 영속성 컨텍스트에서 관리하던 엔티티가 영속성 컨텍스트에서 분리된 상태입니다.
    //  2. 비영속 엔티티와의 가장 큰 차이점은 식별자(ID) 보유 여부입니다. (ID가 있으면 준영속, 없으면 비영속입니다.)
    
    // 비영속 엔티티 만들기
    User user = User.createUser("박영희", "park@example.com", Gender.FEMALE);
    
    // 트랜잭션 시작
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    
    try {
      
      // 비영속 -> 영속 엔티티로 바꾸기
      em.persist(user);
      
      // Dirty Checking
      user.setUsername("고영희");
      user.setEmail("ko@example.com");
      
      // 영속 -> 준영속 엔티티로 바꾸기
      // 준영속 엔티티는 Dirty Checking 동작하지 않습니다.
      // 오직 영속 엔티티만 Dirty Checking 동작합니다.
      em.detach(user);      
      
      // 트랜잭션 커밋
      tx.commit();
      
    } catch (Exception e) {
      tx.rollback();
      throw e;
    }
    
    assertEquals("고영희", em.find(User.class, 1L).getUsername());
    
  }
  
  @Test
  @DisplayName("준영속 엔티티 clear() 테스트")
  void clearTest( ) {
    
    // clear() 메소드는 모든 영속 엔티티를 준영소 엔티티로 바꿉니다.
    
    User user = User.createUser("박영희", "park@example.com", Gender.FEMALE);
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    try {
      em.persist(user);  //------ user 엔티티를 영속성 컨텍스트에 저장
      em.flush();  //------------ INSERT 쿼리 실행
      em.find(User.class, 1);  // 영속성 컨텍스트에서 ID가 1인 User 엔티티 조회 : 성공
      em.find(User.class, 1);  // 영속성 컨텍스트에서 ID가 1인 User 엔티티 조회 : 성공
      em.clear();  //------------ 영속성 컨텍스트에 있는 모든 엔티티를 준영속 상태로 변경 (영속성 컨텍스트에는 아무 엔티티도 없음)
      em.find(User.class, 1);  // 영속성 컨텍스트에서 ID가 1인 User 엔티티 조회 : 실패 (DB로 SELECT 쿼리를 날림)
    } catch (Exception e) {
      tx.rollback();
      throw e;
    }
    
  }
  
  @Test
  @DisplayName("준영속 엔티티 merge() 테스트")
  void mergeTest() {
    
    //----- merge()
    //  1. 준영속 상태의 엔티티를 영속성 컨텍스트에 반환합니다.
    //  2. merge() 동작
    //    1) 준영속 엔티티를 merge()에 전달합니다.
    //    2) 전달 받은 준영속 엔티티의 ID를 이용해 영속성 컨텍스트의 1차 캐시에서 엔티티를 조회합니다.
    //      2-1) 1차 캐시에 있으면 준영속 엔티티의 모든 필드 값을 1차 캐시에 있는 영속 엔티티에 덮어쓰기합니다.
    //      2-2) 1차 캐시에 없으면 DB에서 조회해서 1차 캐시에 저장한 뒤 준영속 엔티티의 모든 필드 값을 덮어쓰기합니다.
    //    3) 그렇게 만든 새로운 영속 엔티티를 반환합니다.
    
    User user = User.createUser("박영희", "park@example.com", Gender.FEMALE);
    
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    
    try {
      
      em.persist(user);  //------ user 엔티티를 영속성 컨텍스트에 저장
      em.flush();  //------------ INSERT 쿼리 실행
      
      em.detach(user);  //------- user 엔티티를 준영속 상태로 변경 (1차 캐시에서 user 엔티티 제거)
      
      User mergedUser = em.merge(user);   // 1차 캐시에서 user 엔티티의 ID를 가진 엔티티를 찾지만, 없기 때문에 DB에서 SELECT 해서 가져옵니다.
      User mergedUser2 = em.merge(user);  // 1차 캐시에서 user 엔티티의 ID를 가진 엔티티를 찾습니다.

      assertTrue(em.contains(mergedUser));  // merge()가 반환한 엔티티는 영속 엔티티입니다.
      assertTrue(em.contains(mergedUser2));  // merge()가 반환한 엔티티는 영속 엔티티입니다.
      
      // assertTrue(em.contains(user));  //------ merge() 이후에도 준영속 엔티티는 여전히 그대로입니다. (실패)
      
      tx.commit();
      
    } catch (Exception e) {
      tx.rollback();
      throw e;
    }
    
  }
  
  @Test
  @DisplayName("준영속 엔티티 merge()와 Dirty Checking 테스트")
  void mergeDirtyCheckingTest() {
    
    // 1. 새로운 User 엔티티 생성 (비영속 상태)
    User user = User.createUser("이말자", "lee@example.com", Gender.FEMALE);
    
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    
    try {
      
      // 2. 엔티티를 영속성 컨텍스트에 저장 (영속 상태로 변경)
      //   - 1차 캐시에 저장됨
      //   - 스냅샷 생성 (원본 데이터 보관)
      em.persist(user);
      
      // 3. 즉시 INSERT 쿼리를 DB에 실행
      //   - 영속성 컨텍스트 내용을 DB와 동기화
      em.flush();
      
      // 4. user 엔티티를 준영속 상태로 변경 
      //   - 1차 캐시에서 제거됨
      //   - 더 이상 엔티티 매니저가 관리하지 않음 (변경감지: Dirty Check 대상 아님)
      em.detach(user);
      
      // 5. 준영속 상태의 엔티티 값 변경
      //   - 엔티티 매니저가 관리하지 않으므로 이 변경사항은 감지되지 않음
      //   - 단순한 객체 상태 변경
      user.setUsername("최말자");
      user.setEmail("choi@example.com");
      
      // 6. merge() 실행으로 준영속 엔티티를 영속 상태로 병합
      // 
      //   1) user 엔티티의 식별자(ID)로 1차 캐시에서 엔티티 조회 시도
      //   2) 1차 캐시에 없으므로 DB에서 SELECT 쿼리로 조회
      //   3) 조회된 영속 엔티티에 준영속 상태인 user의 모든 필드값 복사
      //      (이말자 → 최말자, lee@example.com → choi@example.com)
      //   4) 새로운 영속 엔티티(mergedUser) 반환
      //      (최말자, choi@example.com를 가짐)
      User mergedUser = em.merge(user);
      log.info("{}", mergedUser);
      
      // 7. 트랜잭션 커밋 - 여기서 UPDATE 쿼리 실행!
      //
      //   1) flush() 자동 호출
      //   2) 변경감지(Dirty Checking) 실행
      //      - 영속 엔티티 mergedUser와 스냅샷 비교
      //      - 여기서 스냅샷은 DB에서 조회해 온 데이터를 새로운 스냅샷으로 남긴 것
      //      - 변경사항 감지: 이름과 이메일이 달라졌음을 여기서 인지
      //   3) UPDATE 쿼리 생성하여 쓰기 지연 SQL 저장소에 보관
      //   4) DB에 UPDATE 쿼리 실행
      tx.commit();
      
    } catch (Exception e) {
      tx.rollback();
      throw e;
    }
    
  }
  
}