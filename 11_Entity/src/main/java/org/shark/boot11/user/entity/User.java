package org.shark.boot11.user.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.shark.boot11.user.enums.UserGrade;
import org.springframework.web.bind.annotation.CrossOrigin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "tbl_user")

@Getter
@Setter
public class User {
   
  //----- 식별자 설정 3 :   @TableGenerator(name = "uidGen") + @GeneratedValue(strategy = GenerationType.TABLE, generator ="uidGen")
  //        - 식별값을 생성하는 테이블을 사용하는 방식
  //        - 대부분의 DB에서 범용적으로 사용 가능
  @Id
  @TableGenerator(
      name = "uidGen"
    , table = "uid_seq" 
    , pkColumnName = "entity"
    , pkColumnValue = "user"
    , valueColumnName = "nextval"
    , initialValue = 0
    , allocationSize = 1
      )
  @GeneratedValue(strategy = GenerationType.TABLE, generator ="uidGen")
  private Long uid;
  
  @Column(nullable = false)  //---- not null
  private String username;
  
  @Column(unique = true)
  private String email;
  
  @Column(name = "phone_number")  //---- 칼럼명
  private String phoneNumber;
  
  @Column(length = 100)   //---- varchar(100)
  private String address;
  
  @Column(columnDefinition = "text") //---- varchar 대신 text
  private String memo;
  
  @CreationTimestamp  //----- 엔티티 정보가 테이블에 INSERT 될 때 자동으로 현재 시간을 입력
  @Column(name ="created_at", updatable = false)  //----  updatable = false   :  내용 수정 불가한 칼럼                                          
  private LocalDateTime createdAt;               //     LocalDateTime         :   datetime(6). YYYY-MM-DD HH:MM:SS.000000
  
  @Enumerated(EnumType.STRING) //---- enum 데이터를 문자열로 저장
  @Column(name = "user_grade", length = 7)
  private UserGrade userGrade;

  public User() {
    
  }
  
  public User(String username, String email, String phoneNumber, String address, String memo, UserGrade userGrade) {
    super();
    this.username = username;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.address = address;
    this.memo = memo;
    this.userGrade = userGrade;
  }

  @Override
  public String toString() {
    return "User [uid=" + uid + ", username=" + username + ", email=" + email + ", phoneNumber=" + phoneNumber
        + ", address=" + address + ", memo=" + memo + ", createdAt=" + createdAt + ", userGrade=" + userGrade + "]";
  }
  
  
}
