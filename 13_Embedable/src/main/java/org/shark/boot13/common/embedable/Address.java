package org.shark.boot13.common.embedable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Setter
@Getter
public class Address {

  
  private String postcode;
  
  private String city;
  
  @Column(name = "street_addr")
  private String streetAddr;
  
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
    return "Address [postcode=" + postcode + ", city=" + city + ", streetAddr=" + streetAddr + "]";
  }
  
}
