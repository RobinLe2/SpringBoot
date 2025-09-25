package org.shark.boot11.product.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

//---- @Entity
//     엔티티로 등록된 클래스(Object)는 부트 앱이 실행될 때 자동으로 인식되어 테이블(Relational)과 매핑(Mapping)됩니다.
//     - 엔티티명 == 테이블명
//     - 필드명   == 칼럼명

@Setter
@Getter
@Entity
public class Product {
  //---- @Id
  //     엔티티 설정 시 필수 사항입니다.
  //---- 식별자 설정 1 : 식별값을 직접 생성하는 방식
  @Id
  private String code;
  
  // String -> varchar(255)
  private String name;
  
  // Long -> bingint
  private Long stock;
  
  // Integer -> integer
  private Integer price;
  
  // LocalDate -> date
  private LocalDate expiredAt;
  
  
  
  public Product() {
   
  }



  public Product(String code, String name, Long stock, Integer price, LocalDate expiredAt) {
    super();
    this.code = code;
    this.name = name;
    this.stock = stock;
    this.price = price;
    this.expiredAt = expiredAt;
  }



  @Override
  public String toString() {
    return "Product [code=" + code + ", name=" + name + ", stock=" + stock + ", price=" + price + ", expiredAt="
        + expiredAt + "]";
  }
  
  
  
}
