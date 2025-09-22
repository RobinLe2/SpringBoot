package org.shark.boot05.board.controller;

import java.util.Map;
import java.util.NoSuchElementException;

import org.shark.boot05.board.dto.BoardDTO;
import org.shark.boot05.board.service.BoardService;
import org.shark.boot05.common.dto.PageDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/*
 * 홈화면   GET   /
 * 목록     GET   /board/list?page=1&size=20&sort=DESC
 * 등록폼   GET   /board/write
 * 등록     POST  /board/create
 * 상세     GET   /board/detail?bid=1
 * 수정폼   GET   /board/edit?bid=1
 * 수정     POST  /board/update
 * 삭제     POST  /board/delete
 */

@RequiredArgsConstructor
@Controller
public class BoardController {

  private final BoardService boardService;
  
  @GetMapping("/")
  public String home() {
    return "home";  //----- SpringResourceTemplateResolver에 의해서 prefix, suffix가 자동으로 추가됩니다.
                    //----- prefix="/templates/"
                    //----- suffix=".html"
  }
  
  @GetMapping("/board/list")
  public String list(
      PageDTO dto,
      @RequestParam(value = "sort", defaultValue = "DESC") String sort,
      Model model
  ) {
    if (sort.isEmpty() || (!sort.equalsIgnoreCase("asc") && !sort.equalsIgnoreCase("desc"))) {
      sort = "DESC";
    }
    Map<String, Object> result = boardService.getBoardList(dto, sort);
    model.addAttribute("boardList", result.get("boardList"));
    model.addAttribute("pageDTO", result.get("pageDTO"));
    return "board/list";
  }
  
  @GetMapping("/board/detail")
  public String detail(
      @RequestParam(value = "bid", required = false) Long bid, 
      Model model
  ) {
    if (bid == null) {
      throw new IllegalArgumentException("게시글 ID 정보를 확인할 수 없습니다.");
    }
    if (bid <= 0) {
      throw new IllegalArgumentException("게시글 ID는 1 이상의 정수입니다.");
    }
    BoardDTO foundBoard = boardService.getBoardById(bid);
    if (foundBoard == null) {
      throw new NoSuchElementException("게시글 ID가 " + bid + "인 게시글이 존재하지 않습니다.");
    }
    model.addAttribute("board", foundBoard);
    return "board/detail";
  }
  
  @GetMapping("/board/write")
  public String writeForm(@ModelAttribute(value = "board") BoardDTO board) {
    return "board/writeForm";
  }
  
  @PostMapping("/board/create")
  public String create(
      @Valid BoardDTO board,        //----- BoardDTO 유효성 검사를 합니다.
      BindingResult bindingResult,  //----- @Valid 검증 결과를 저장합니다.
      Model model,
      RedirectAttributes redirectAttr
  ) {
    //----- 유효성 검사를 통과하지 못한 경우 writeForm으로 되돌아 갑니다.
    if (bindingResult.hasErrors()) {
      model.addAttribute("errors", bindingResult);
      model.addAttribute("board", board);
      return "board/writeForm";
    }
    //----- 등록 시 오류가 발생하면 writeForm으로 리다이렉트합니다.
    try {
      boolean success = boardService.createBoard(board);
      redirectAttr.addFlashAttribute("msg", success  ? "등록 성공" : "등록 실패");
      return "redirect:/board/list";
    } catch (Exception e) {
      redirectAttr.addFlashAttribute("insertError", "게시글 등록 중 오류가 발생했습니다.")
                  .addFlashAttribute("board", board);
      return "redirect:/board/write";
    }
  }
  
  @GetMapping("/board/edit")
  public String editForm(
      @RequestParam(value = "bid", required = false) Long bid, 
      Model model
  ) {
    if (bid == null) {
      throw new IllegalArgumentException("게시글 ID 정보를 확인할 수 없습니다.");
    }
    if (bid <= 0) {
      throw new IllegalArgumentException("게시글 ID는 1 이상의 정수입니다.");
    }
    BoardDTO foundBoard = boardService.getBoardById(bid);
    if (foundBoard == null) {
      throw new NoSuchElementException("게시글 ID가 " + bid + "인 게시글이 존재하지 않습니다.");
    }
    model.addAttribute("board", foundBoard);
    return "board/editForm";
  }
  
  @PostMapping("/board/update")
  public String update(
      @Valid BoardDTO board,        //----- BoardDTO 유효성 검사를 합니다.
      BindingResult bindingResult,  //----- @Valid 검증 결과를 저장합니다.
      Model model,
      RedirectAttributes redirectAttr) {
    if (bindingResult.hasErrors()) {
      if (bindingResult.hasFieldErrors("title")) {
        model.addAttribute("titleError", bindingResult.getFieldError("title").getDefaultMessage());
      }
      if (bindingResult.hasFieldErrors("content")) {
        model.addAttribute("contentError", bindingResult.getFieldError("content").getDefaultMessage());
      }
      model.addAttribute("board", board);
      return "board/editForm";
    }
    try {
      Boolean success = boardService.updateBoard(board);
      redirectAttr.addFlashAttribute("msg", success ? "수정 성공" : "수정 실패")
                  .addAttribute("bid", board.getBid());
      return "redirect:/board/detail";
    } catch (Exception e) {
      redirectAttr.addFlashAttribute("updateError", "게시글 수정 중 오류가 발생했습니다.")
                  .addAttribute("bid", board.getBid());
      return "redirect:/board/edit";      
    }
  }
  
  @PostMapping("/board/delete")
  public String delete(
      @RequestParam(value = "bid", required = false) Long bid, 
      RedirectAttributes redirectAttr
  ) {
    if (bid == null) {
      throw new IllegalArgumentException("게시글 ID 정보를 확인할 수 없습니다.");
    }
    if (bid <= 0) {
      throw new IllegalArgumentException("게시글 ID는 1 이상의 정수입니다.");
    }
    try {
      boolean success = boardService.deleteBoard(bid);
      redirectAttr.addFlashAttribute("msg", success ? "삭제 성공" : "삭제 실패");
      return "redirect:/board/list";
    } catch (Exception e) {
      redirectAttr.addFlashAttribute("deleteError", "게시글 삭제 중 오류가 발생했습니다.")
                  .addAttribute("bid", bid);
      return "redirect:/board/edit";
    }

  }
  
}
