package com.sparta.assignment_lv1.controller;

import com.sparta.assignment_lv1.dto.CommentRequestDto;
import com.sparta.assignment_lv1.dto.CommentResponseDto;
import com.sparta.assignment_lv1.dto.MsgAndHttpStatusDto;
import com.sparta.assignment_lv1.security.UserDetailsImpl;
import com.sparta.assignment_lv1.serivce.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{id}")
    public CommentResponseDto createComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(id, requestDto, userDetails);
    }

    //해당 게시글의 모든 댓글 가져오기 오름차순
    @GetMapping("/note/{id}")
    public List<CommentResponseDto> getComments(@PathVariable Long id) {
        return commentService.getComments(id);
    }


    @PutMapping("/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(id, requestDto, userDetails);
    }

    @DeleteMapping("{id}")
    public MsgAndHttpStatusDto deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(id, userDetails);
    }
}
