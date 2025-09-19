package org.shark.boot06.user.exception;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserNotFoundException extends RuntimeException {
 
  private static final long serialVersionUID = 7136258892847399314L;

  public UserNotFoundException(String message) {
    super(message);
  }
}
