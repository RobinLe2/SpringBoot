package org.shark.boot10.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardDTO {
  private Long bid;
  private String title;
  private String content;
  private String createdAt;
}
