package org.shark.boot10.board.controller;

import org.shark.boot10.board.dto.BoardDTO;
import org.shark.boot10.board.service.AutoSaveService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/boards")
@RequiredArgsConstructor
@RestController
public class BoardApiController {
  
  private final AutoSaveService autoSaveService;
  
  @PostMapping("/autosave")
  public ResponseEntity<String> saveDraft(
      @RequestBody BoardDTO boardDTO
      ,HttpSession session
      ){
    Long uid = (Long) session.getAttribute("uid");
    if (uid == null) {
      uid = 1L;
    }
    autoSaveService.saveDraft(uid, boardDTO);
    return ResponseEntity.ok("Draft Saved!");
  }

  @GetMapping("/autosave")
  public ResponseEntity<BoardDTO> loadDraft(HttpSession session){
    Long uid = (Long) session.getAttribute("uid");
    if (uid == null) {
      uid = 1L;
    }
    BoardDTO draftDTO = autoSaveService.loadDraft(uid);
    if (draftDTO == null) {
      return ResponseEntity.noContent().build();
    }else {
      return ResponseEntity.ok(draftDTO);
    }
  }
  
  @PostMapping
  public ResponseEntity<String> saveBoard(
      HttpSession session
      ){
    Long uid = (Long) session.getAttribute("uid");
    if (uid == null) {
      uid = 1L;
    }
    autoSaveService.deleteDraft(uid);
    //db에 진짜 저장
    
    
    return ResponseEntity.ok("Draft Deleted!");
  }
  
}
