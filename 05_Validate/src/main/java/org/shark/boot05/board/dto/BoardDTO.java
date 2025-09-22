package org.shark.boot05.board.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * @Null
 *   null 값만 허용합니다.
 *   
 * @NotNull
 *   null 값을 허용하지 않습니다.
 *   빈 문자열("")이나 공백은 허용됩니다.
 *   
 * @NotEmpty
 *   null과 빈 문자열("")을 허용하지 않습니다.
 *   공백 문자(" ")는 허용됩니다.
 *   
 * @NotBlank
 *   null, 빈 문자열(""), 공백만 있는 문자열(" ")을 모두 허용하지 않습니다.
 *   공백이 아닌 문자를 하나 이상 포함해야 합니다.
 *   
 * @Email
 *   올바른 형식의 이메일 주소여야 합니다.
 *   
 * @Pattern(regexp = "")
 *   지정된 정규식을 만족해야 합니다.
 *   예시: @Pattern(regexp = "01(?:0|1|[6~9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$")
 *   
 * @Min(value = ) / @Max(value = )
 *   지정된 값 이상/이하여야 합니다. (정수형)
 *   예시: @Min(value = 1), @Max(value = 100)
 *   
 * @DecimalMin(value = "") / @DecimalMax(value = "")
 *   지정된 값 이상/이하여야 합니다. (실수형, 문자열로 지정)
 *   예시: @DecimalMin(value = "1.0"), @DecimalMax(value = "100.0")
 *   
 * @Positive / @PositiveOrZero
 *   양수여야 합니다. / 양수 또는 0이어야 합니다.
 *   
 * @Negative / @NegativeOrZero
 *   음수여야 합니다. / 음수 또는 0이어야 합니다.
 *   
 * @Size(min = , max = )
 *   문자열의 길이 또는 컬렉션의 크기가 지정된 범위 내에 있어야 합니다.
 *   예시: @Size(min = 2, max = 10)
 *   
 * @AssertTrue / @AssertFalse
 *   값이 항상 true여야 합니다. / 항상 false여야 합니다.
 *   
 * @Future / @FutureOrPresent
 *   현재보다 미래여야 합니다. / 현재 또는 미래여야 합니다.
 *   
 * @Past / @PastOrPresent
 *   현재보다 과거여야 합니다. / 현재 또는 과거여야 합니다.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class BoardDTO {
  
  private Long bid;
  
  @NotBlank(message = "제목은 필수입니다.")
  @Size(max = 100, message = "제목의 최대 글자 수는 100자입니다.")
  private String title;
  
  @Size(max = 100, message = "내용의 최대 글자 수는 100자입니다.")
  private String content;
  
  private LocalDateTime createdAt;
  
}
