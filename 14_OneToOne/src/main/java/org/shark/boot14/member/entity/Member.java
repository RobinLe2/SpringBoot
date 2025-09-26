package org.shark.boot14.member.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "members")

@Getter
@Setter
public class Member {

  //---- 1:1 연관관계 주인(외래키 보유)
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "mid")
  private Long id;
  
 
  private String name;
  private String mobile;
  
  //---- 외래키(Long lockerId) 
  @OneToOne(                           //------ Member : Locker 1:1 연관관계
      cascade = CascadeType.ALL,       //----- 영속성 전이
                                       //   1. 부모 엔티티의 영속성 상태 변화를 자식 엔티티에게 전이시키는 기능입니다.
                                       //   2. 종류
                                       //     1) ALL      : 모든 영속성 상태 전이( 부모와 자식의 라이프 사이클이 동일할 때 사용)
                                       //     2) PERSIST  : 영속화 시에만 전이 (자신도 함께 저장)
                                       //     3) REMOVE   : 삭제 시에만 전이 (자식도 함께 삭제)
      fetch = FetchType.LAZY           //----- 로딩 전략 
                                       //    1. 연관 엔티티를 언제 조회할지 결정하는 전략입니다.
                                       //    2.  종류
                                       //      1) EAGER : 즉시 로딩. 한 번에 모든 데이터를 로딩합니다.
                                       //      2) LAZY  : 지연 로딩. 필요한 데이터만 로딩합니다.
      )                        
  @JoinColumn(name = "locker_id")   //------ 외래키의 칼럼명
  private Locker locker;           // 외래키를 관리하는 칼럼명을 양방향 매핑을 위해서 mappedBy="locker" 
  
  protected Member() { }
  
  public static Member createMember(String name, String mobile) {
    Member member = new Member();
    member.name = name;
    member.mobile = mobile;
    return member;
  }
  
  //---- 연관관계 설정을 위한 비즈니스 메소드
  
  // Locker 배정
  public void assignLocker(Locker locker) {
    this.locker = locker;
    if (locker != null) {
    locker.setMember(null);  // 양방향 연관관계에 때문에 추가되는 코드
    }
  }
  
  // Locker 해제
  public void removeLocker(Locker locker) {
    if(this.locker != null) 
    this.locker.setMember(null);
    this.locker = null;
  }

  @Override
  public String toString() {
    return "Member [id=" + id + ", name=" + name + ", mobile=" + mobile + ", locker's id=" + locker.getId() + "]";
  }
  
  
}

