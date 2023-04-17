package com.sparta.assignment_lv1.controller;

import com.sparta.assignment_lv1.dto.NoteRequestDto;
import com.sparta.assignment_lv1.dto.NoteResponseDto;
import com.sparta.assignment_lv1.entity.Note;
import com.sparta.assignment_lv1.serivce.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * @RequestBody 이 어노테이션이 붙은 파라미터에는 http 요청의 본문(body)이 그대로 전달된다.
 */
@RestController
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping("/") // 처음 홈페이지
    public ModelAndView home() {
        return new ModelAndView("index");
    }


    @GetMapping("/api/notes") //  전체 게시물 목록 조회
    public List<Note> getNotes() {
        return noteService.getNotes();
    }

    @PostMapping("/api/note") //  게시글 작성
    private Note createNote(@RequestBody NoteRequestDto requestDto) {
        return noteService.createNote(requestDto);
    }

    @GetMapping("/api/note/{id}") // 선택한 게시글 조회 -> 코드 수정 및 HTML 수정 필요
    public Note getNote(@PathVariable Long id) {
        return noteService.getNote(id);
    }


    @PutMapping("/api/note/{id}") //  선택한 게시글 수정
    public NoteResponseDto updateNote(@PathVariable Long id, @RequestBody NoteRequestDto requestDto) {
        return noteService.updateNote(id, requestDto);
    }

//    @PutMapping("/api/note/{id}") //  선택한 게시글 수정
//    public Note updateNote(@PathVariable Long id, @RequestBody NoteResponseDto responseDto) {
//        return noteService.updateNote(id, responseDto);
//    }

    @DeleteMapping("/api/note/{id}") //  선택한 게시글 삭제
    public String deleteNote(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
        String password = requestBody.get("password");
        return noteService.deleteNote(id, password);
    }
    //이 코드에서는 @RequestBody 어노테이션을 사용하여 Map<String, String> 타입의 객체를 주입받고,
    // 해당 객체에서 get() 메서드를 사용하여 "password" 필드의 값을 추출
    // 추출한 비밀번호 값을 이용하여 비밀번호 일치 여부를 확인한 후 삭제 작업을 수행

}
