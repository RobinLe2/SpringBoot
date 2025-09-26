package org.shark.boot14.company.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "address")
@Setter
@Getter
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "addr_id")
  private Long id;
  
  private String postcode;
  
  private String city;
  
  @Column(name = "street_addr")
  private String streetAddr;

  
  @OneToOne(
      mappedBy = "address",
      fetch=FetchType.LAZY)
  private Employee employee;
  
  protected Address() {}
  
  public static Address createAddress(String postcode, String city , String streetAddr) {
    Address address = new Address();
    address.postcode = postcode;
    address.city = city;
    address.streetAddr = streetAddr;
    return address;
  }

  @Override
  public String toString() {
    return "Address [id=" + id + ", postcode=" + postcode + ", city=" + city + ", streetAddr=" + streetAddr
        + ", employee=" + employee + "]";
  }



  
  
}
