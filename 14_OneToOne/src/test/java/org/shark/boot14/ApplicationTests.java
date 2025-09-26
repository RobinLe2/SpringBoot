package org.shark.boot14;

import java.util.jar.Attributes.Name;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.shark.boot14.common.util.JpaUtil;
import org.shark.boot14.company.entity.Address;
import org.shark.boot14.company.entity.Employee;
import org.shark.boot14.member.entity.Locker;
import org.shark.boot14.member.entity.Member;
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
	@DisplayName("1:1 연관관계 저장 테스트")
	void createOneToOneRelationshipTest() {
	  Member member = Member.createMember("이철수", "010-3333-3333");
	  Locker locker = Locker.createLocker("D1");
	  member.assignLocker(locker); // 연관관계가 설정되는 코드
	      
	  EntityTransaction tx = em.getTransaction();
	  
    tx.begin();
    
    try {
      em.persist(member);
      em.flush();
      
      em.clear();
      
      Long id = member.getId();
      Member foundMember = em.find(Member.class, id);  // SELECT 쿼리 실행  
                                                        // fetch=FetchType.EAGER : Member와 연관된 Locker 모두 조회(join 발생)
                                                        // fetch=FetchType.LAZY : Member만 조회
      log.info("Member's name: {}" , member.getName());
      Locker foundLocker =foundMember.getLocker();      // fetch=FetchType.LAZY : Locker를 조회
      String location = foundLocker.getLocation();
      log.info("Locker's loation : {}" , location);
      
      
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      throw e;
      
      // TODO: handle exception
    }
	 
	}
	
	@Test
	@DisplayName("Employee: Address")
	void createOneToOneRelationshipEAtEST() {
	  Employee employee = Employee.createEmployee("이창민");
	  Address address = Address.createAddress("12345", "seoul", "seoul123");
	  employee.assignAddress(address);
	  
	  EntityTransaction tx = em.getTransaction();
    tx.begin();
    
    try {
      em.persist(employee);
      em.flush();
      
      em.clear();
      
      Long Id = employee.getId();
      Employee foundemployee = em.find(Employee.class, Id);
      
      Address foundAddress = foundemployee.getAddress();
      String postcode = foundAddress.getPostcode();
      
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      throw e;
      
      // TODO: handle exception
    }
	  
	}
	
	@Test
	@DisplayName("양방향 매핑 테스트")
	void biDirectionMappingTest() {
	  
	  Member member = Member.createMember("제시카", "010-9999-9999");
	  Locker locker = Locker.createLocker("B3");
	  member.assignLocker(locker);
	  
	  EntityTransaction tx = em.getTransaction();
    tx.begin();
    
    try {
      
      em.persist(member);
      
      em.flush();
     
      Long locker_id = locker.getId();
  
      em.clear();
      
      Locker foundLocker = em.find(Locker.class, locker_id);
      
      log.info("foundLocker: {}" , foundLocker);
      
      // foundLocker를 이용하는 Member 조회
      Member foundMember = foundLocker.getMember();
      log.info("founderMember's name : {}", foundMember.getName());
      
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      throw e;
      
      // TODO: handle exception
    }
	}
	
	@Test
  @DisplayName("양방향 매핑 테스트")
  void biDirectionMappingTest2() {
    
	  
	  Employee employee = Employee.createEmployee("이창민");
    Address address = Address.createAddress("12345", "seoul", "seoul123");
    employee.assignAddress(address);
    
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    
    try {
      
      em.persist(employee);
      
      em.flush();
     
      Long addr_id= address.getId();
  
      em.clear();
      
      Address foundaddr = em.find(Address.class, addr_id);
      
      log.info("foundaddr: {}" , foundaddr);
      
      // foundLocker를 이용하는 Member 조회
      Employee foundEmployee = foundaddr.getEmployee();
      log.info("foundEmployee'S name : {}", foundEmployee.getName());
      
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      throw e;
      
      // TODO: handle exception
    }
  }

}
