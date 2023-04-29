package com.sparta.assignment_lv1.controller;

import com.sparta.assignment_lv1.dto.MsgAndHttpStatusDto;
import com.sparta.assignment_lv1.dto.NoteRequestDto;
import com.sparta.assignment_lv1.dto.NoteResponseDto;
import com.sparta.assignment_lv1.entity.Note;
import com.sparta.assignment_lv1.security.UserDetailsImpl;
import com.sparta.assignment_lv1.serivce.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @RequestBody 이 어노테이션이 붙은 파라미터에는 http 요청의 본문(body)이 그대로 전달된다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NoteController {

    private final NoteService noteService;

    @GetMapping("/notes") //  전체 게시물 목록 조회
    public ResponseEntity<List<NoteResponseDto>> getNotes() {
        return noteService.getNotes();
    }

    @PostMapping("/note") //  게시글 작성
    private ResponseEntity<Note> createNote(@RequestBody NoteRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return noteService.createNote(requestDto, userDetails);
    }

    @GetMapping("/note/{note_id}") // 선택한 게시글 조회 -> 코드 수정 및 HTML 수정 필요
    public ResponseEntity<NoteResponseDto> getNote(@PathVariable Long note_id) {
        return noteService.getNote(note_id);
    }


    @PutMapping("/note/{note_id}") //  선택한 게시글 수정
    public ResponseEntity<NoteResponseDto> updateNote(@PathVariable Long note_id, @RequestBody NoteRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return noteService.updateNote(note_id, requestDto, userDetails);
    }

//    @PutMapping("/api/note/{id}") //  선택한 게시글 수정
//    public Note updateNote(@PathVariable Long id, @RequestBody NoteResponseDto responseDto) {
//        return noteService.updateNote(id, responseDto);
//    }

    @DeleteMapping("/note/{note_id}") //  선택한 게시글 삭제
    public ResponseEntity<MsgAndHttpStatusDto> deleteNote(@PathVariable Long note_id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return noteService.deleteNote(note_id, userDetails);
    }
    //이 코드에서는 @RequestBody 어노테이션을 사용하여 Map<String, String> 타입의 객체를 주입받고,
    // 해당 객체에서 get() 메서드를 사용하여 "password" 필드의 값을 추출
    // 추출한 비밀번호 값을 이용하여 비밀번호 일치 여부를 확인한 후 삭제 작업을 수행

}
