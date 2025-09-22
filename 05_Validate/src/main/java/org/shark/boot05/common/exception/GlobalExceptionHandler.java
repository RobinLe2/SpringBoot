package org.shark.boot05.common.exception;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String handleIllegalArgumentException(IllegalArgumentException e, Model model) {
    model.addAttribute("error", "잘못된 요청입니다. (" + e.getMessage() + ")");
    return "error/400";
  }
  
  @ExceptionHandler(NoSuchElementException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String handleNoSuchElementException(NoSuchElementException e, Model model) {
    model.addAttribute("error", "요청하신 자원을 찾을 수 없습니다. (" + e.getMessage() + ")");
    return "error/404";
  }
  
}
