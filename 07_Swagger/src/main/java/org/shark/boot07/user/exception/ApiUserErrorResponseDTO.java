package org.shark.boot07.user.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ApiUserErrorResponseDTO {
  private String errorCode;
  private String errorMessage;        // 필수 값 누락
  private String errorDetailMessage;  // 아이디는 필수입니다.
}
