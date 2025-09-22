package org.shark.boot07.user.dto.response;

import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
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

@Schema(description = "User API 성공 시 응답")
public class ApiUserResponseDTO {
  
  @Schema(description = "응답코드"
        , nullable = false
        , allowableValues = {"200", "201"})
  private int status;
  
  @Schema(description = "응답메시지"
        , nullable = false)
  private String message;
  
  @Schema(description = "응답결과"
        , nullable = true)
  private Map<String, Object> results;
  
}
