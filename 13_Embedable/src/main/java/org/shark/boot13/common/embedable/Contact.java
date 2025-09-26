package org.shark.boot13.common.embedable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Setter
@Getter
public class Contact {

  private String email;
  
  private String tel;
  
  private String fax;
  
  protected Contact() {}
  
  public static Contact createContact(String email, String tel, String fax) {
    Contact contact = new Contact();
    contact.email = email;
    contact.tel = tel;
    contact.fax = fax;
    return contact;
  }
  
  @Override
  public String toString() {
    return "Contact [email=" + email + ", tel=" + tel + ", fax=" + fax + "]";
  }
  
}
