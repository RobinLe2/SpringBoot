package org.shark.boot07.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 페이징 정보 데이터 전송 객체
 * 
 * ===== 필드 값을 채우는 방식 =====
 * 1. 요청 파라미터 : page, size
 * 2. 쿼리 실행     : totalItem
 * 3. 계산          : offset, totalPage, beginPage, endPage
 */

@Getter
@Setter
public class PageDTO {

  private int page = 1;   //----- 현재 페이지 번호 (요청 파라미터. 디폴트 1)
  private int size = 20;  //----- 한 페이지에 표시할 항목의 개수 (요청 파라미터. 디폴트 20)
  
  private long totalItem; //----- 전체 항목의 개수

  private long offset;    //----- 각 페이지의 시작 인덱스
  private int totalPage;  //----- 전체 페이지의 개수
  private int beginPage;  //----- 현재 블록의 시작 페이지
  private int endPage;    //----- 현재 블록의 끝 페이지
  
}