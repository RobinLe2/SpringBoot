package org.shark.boot06.user.exception;

public class UserNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 7136258892847399314L;

  public UserNotFoundException(String message) {
    super(message);
  }
  
}
