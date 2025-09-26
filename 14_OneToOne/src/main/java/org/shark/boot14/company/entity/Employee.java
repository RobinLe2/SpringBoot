package org.shark.boot14.company.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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
@Table(name = "employees")
  
@Getter
@Setter
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "eid")
  private Long id;
  

  private String name;
  
  @OneToOne(
      cascade = CascadeType.ALL,
      
      fetch = FetchType.LAZY)
  @JoinColumn(name = "addr_id")
  private Address address;
  
  protected Employee() {}
    
  public static Employee createEmployee(String name) {
      Employee employee = new Employee();
      employee.name = name;
      return employee;
    
  }
  
  public void assignAddress(Address address) {
    this.address = address;
  }
  

  @Override
  public String toString() {
    return "Employee [id=" + id + ", address's id=" + address.getId() + "]";
  }
}
