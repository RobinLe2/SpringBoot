package org.shark.boot15;


import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.shark.boot15.categories.entity.Category;
import org.shark.boot15.common.util.JpaUtil;
import org.shark.boot15.products.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
class ApplicationTests {
  @Autowired
  private org.shark.boot15.common.util.JpaUtil jpaUtil;

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
  @DisplayName("일대다 관계의 양방향 탐색 테스트")
  void biDirectionTest() {

    EntityTransaction tx = em.getTransaction();
    tx.begin();

    try {

      // 1. 카테코리를 먼저 저장
      Category snack = Category.createCategory("스낵", "과자류");
      em.persist(snack);

      // 2. 제품을 여러 개 만들어 저장
      Product snack1 = Product.creatProduct("콘칩", 2000.0, 10, snack);
      Product snack2 = Product.creatProduct("새우깡", 3000.0, 20, snack);

      em.persist(snack1);
      em.persist(snack2);

      // 3. 쿼리 실행
      em.flush();

      // 4. 모든 엔티티를 준영속 엔티티로 변경
      em.clear();

      // 5. 양방향 매핑 테스트를 위해서 카테고리로 제품 조회하기 테스트
      Category foundCategory = em.find(Category.class, snack.getId());
      log.info("카테고리명: {}" , foundCategory.getCategoryName());

      for (Product product : foundCategory.getProducts()) {
        log.info("상품명: {}" , product);
      }

      tx.commit();

    } catch (Exception e) {
      tx.rollback();
      throw e;
    }
  }

}
