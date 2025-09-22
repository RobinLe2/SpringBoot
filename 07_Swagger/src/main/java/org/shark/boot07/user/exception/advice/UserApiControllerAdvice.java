package org.shark.boot07.user.exception.advice;

import org.shark.boot07.user.controller.UserApiController;
import org.shark.boot07.user.exception.ApiUserErrorResponseDTO;
import org.shark.boot07.user.exception.UserNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * ----------+--------------------------------------------
 *  응답코드 | 의미 및 세부 코드
 * ----------+--------------------------------------------
 *  400      | Bad Request : 잘못된 요청
 *           |   10 / 중복된 값 입력
 *           |   20 / 필수 값 누락
 *           |   21 / 입력 가능한 크기 벗어남
 *           |   22 / 잘못된 형식의 아이디(이메일) 입력
 *           |   30 / 요청 파라미터 누락
 *           |   40 / 경로 변수 및 요청 파라미터 타입 오류
 *           |   41 / 요청 파라미터 값 오류
 * ----------+--------------------------------------------
 *  404      | Not Found : 해당 정보 없음
 * ----------+--------------------------------------------
 */

@RestControllerAdvice(assignableTypes = {UserApiController.class})  //----- UserApiController 컨트롤러에만 적용
public class UserApiControllerAdvice {

  //----- 전달된 회원번호(uid)를 가진 회원이 존재하지 않는 경우
  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ApiUserErrorResponseDTO> handleUserNotFoundException(UserNotFoundException e) {
    String errorDetailMessage = null;
    switch (e.getErrorCode()) {
    case 1: errorDetailMessage = "회원 정보를 조회할 수 없습니다."; break;
    case 2: errorDetailMessage = "회원 정보를 수정할 수 없습니다."; break;
    case 3: errorDetailMessage = "회원 정보를 삭제할 수 없습니다."; break;
    }
    ApiUserErrorResponseDTO errorDTO = ApiUserErrorResponseDTO.builder()
        .errorCode("404")
        .errorMessage(e.getMessage())
        .errorDetailMessage(errorDetailMessage)
      .build();
    return ResponseEntity.status(404).body(errorDTO);
  }
  
  //----- 중복된 값 입력
  @ExceptionHandler(DuplicateKeyException.class)
  public ResponseEntity<ApiUserErrorResponseDTO> handleDuplicateKeyException(DuplicateKeyException e) {
    ApiUserErrorResponseDTO errorDTO = ApiUserErrorResponseDTO.builder()
        .errorCode("10")
        .errorMessage("중복된 값 입력")
        .errorDetailMessage("동일한 아이디가 이미 존재합니다.")
      .build();
    return ResponseEntity.badRequest().body(errorDTO);
  }
  
  //----- 필수 값 누락, 입력 가능한 크기 벗어남, 잘못된 형식의 아이디(이메일)가 입력된 경우
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiUserErrorResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    String errorCode = null;
    String errorMessage = null;
    String errorDetailMessage = null;
    BindingResult bindingResult = e.getBindingResult();
    if (bindingResult.hasErrors()) {
      switch (bindingResult.getFieldError().getCode()) {
      case "NotBlank":
        errorCode = "20"; errorMessage = "필수 값 누락";
        break;      
      case "Size":
        errorCode = "21"; errorMessage = "입력 가능한 크기 벗어남";
        break;
      case "Pattern":
        errorCode = "22"; errorMessage = "잘못된 형식의 아이디(이메일) 입력";
        break;
      }
      errorDetailMessage = bindingResult.getFieldError().getDefaultMessage();
    }
    ApiUserErrorResponseDTO errorDTO = ApiUserErrorResponseDTO.builder()
        .errorCode(errorCode)
        .errorMessage(errorMessage)
        .errorDetailMessage(errorDetailMessage)
      .build();
    return ResponseEntity.badRequest().body(errorDTO);
  }
  
  //----- 필수 요청 파라미터가 누락된 경우
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ApiUserErrorResponseDTO> handleNoResourceFoundException(MissingServletRequestParameterException e) {
    ApiUserErrorResponseDTO errorDTO = ApiUserErrorResponseDTO.builder()
        .errorCode("30")
        .errorMessage("필수 요청 파라미터 누락")
        .errorDetailMessage("목록 확인을 위한 page 정보는 필수입니다.")
      .build();
    return ResponseEntity.badRequest().body(errorDTO);
  }
  
  //----- 경로 변수 및 요청 파라미터의 타입이 잘못된 경우
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ApiUserErrorResponseDTO> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
    String errorDetailMessage = null;
    switch (e.getName()) {  // e.getName()은 타입 오류가 발생한 경로 변수나 요청 파라미터의 이름
    case "uid": errorDetailMessage = "사용자 정보 식별값은 1 이상의 양의 정수입니다."; break;
    case "page": errorDetailMessage = "page는 1 이상의 양의 정수입니다."; break;
    case "size": errorDetailMessage = "size는 1 이상의 양의 정수입니다."; break;    
    }
    ApiUserErrorResponseDTO errorDTO = ApiUserErrorResponseDTO.builder()
        .errorCode("40")
        .errorMessage("경로 변수 및 요청 파라미터 타입 오류")
        .errorDetailMessage(errorDetailMessage)
        .build();
    return ResponseEntity.badRequest().body(errorDTO);
  }
  
  //----- 정렬 시 sort 파라미터에 ASC/DESC 이외의 값이 전달되는 경우
  @ExceptionHandler(BadSqlGrammarException.class)
  public ResponseEntity<ApiUserErrorResponseDTO> handleBadSqlGrammarException(BadSqlGrammarException e) {
    ApiUserErrorResponseDTO errorDTO = ApiUserErrorResponseDTO.builder()
        .errorCode("41")
        .errorMessage("요청 파라미터 값 오류")
        .errorDetailMessage("사용자 정렬 방식은 ASC 또는 DESC 중 하나입니다.")
        .build();
    return ResponseEntity.badRequest().body(errorDTO);
  }
  
}
