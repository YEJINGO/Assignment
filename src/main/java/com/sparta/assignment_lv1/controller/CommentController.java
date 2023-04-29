package com.sparta.assignment_lv1.controller;

import com.sparta.assignment_lv1.dto.CommentRequestDto;
import com.sparta.assignment_lv1.dto.CommentResponseDto;
import com.sparta.assignment_lv1.dto.MsgAndHttpStatusDto;
import com.sparta.assignment_lv1.security.UserDetailsImpl;
import com.sparta.assignment_lv1.serivce.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(commentId, requestDto, userDetails);
    }

    //해당 게시글의 모든 댓글 가져오기 오름차순
    @GetMapping("/note/{commentId}")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long commentId) {
        return commentService.getComments(commentId);
    }


    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(commentId, requestDto, userDetails);
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<MsgAndHttpStatusDto> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(commentId, userDetails);
    }
}
