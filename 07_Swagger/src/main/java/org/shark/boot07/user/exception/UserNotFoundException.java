package org.shark.boot07.user.exception;

public class UserNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 7136258892847399314L;

  private int errorCode;
  
  public UserNotFoundException(int errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public int getErrorCode() {
    return errorCode;
  }
  
}
