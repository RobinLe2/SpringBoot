package org.shark.boot05.common.util;

import org.shark.boot05.common.dto.PageDTO;
import org.springframework.stereotype.Component;

/**
 * 페이징 계산 유틸리티 클래스
 */

@Component  //----- Spring Container에 PageUtil 타입의 빈이 등록됩니다.
public class PageUtil {

  //----- 한 블록 당 표시할 페이지의 개수
  private static final int PAGE_PER_BLOCK = 10;
 
  /**
   * 페이징 정보를 계산해서 PageDTO에 저장하는 메소드
   */
  public void calculatePaging(PageDTO dto) {
    int page = dto.getPage();
    int size = dto.getSize();
    int totalItem = dto.getTotalItem();
    
    int offset = (page - 1) * size;
    int totalPage = (int) Math.ceil((double) totalItem / size);
    int beginPage = ((page - 1) / PAGE_PER_BLOCK) * PAGE_PER_BLOCK + 1;
    int endPage = Math.min(beginPage + PAGE_PER_BLOCK - 1, totalPage);
    
    dto.setOffset(offset);
    dto.setTotalPage(totalPage);
    dto.setBeginPage(beginPage);
    dto.setEndPage(endPage);
  }
  
}