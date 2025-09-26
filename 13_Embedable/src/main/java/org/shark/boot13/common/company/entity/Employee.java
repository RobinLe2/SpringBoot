package org.shark.boot13.common.company.entity;

import org.shark.boot13.common.embedable.Address;
import org.shark.boot13.common.embedable.Contact;
import org.shark.boot13.common.embedable.Name;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
  
  @Embedded
  private Name name;
  
  
  @Embedded
  private Address address;
  
  protected Employee() {}
    
  public static Employee createEmployee(Name name ,Address address) {
      Employee employee = new Employee();
      employee.name = name;
      employee.address = address;
      return employee;
    
  }

  @Override
  public String toString() {
    return "Employee [id=" + id + ", name=" + name + ", address=" + address + "]";
  }
}
