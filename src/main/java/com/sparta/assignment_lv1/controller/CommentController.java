package com.sparta.assignment_lv1.controller;

import com.sparta.assignment_lv1.dto.CommentRequestDto;
import com.sparta.assignment_lv1.dto.CommentResponseDto;
import com.sparta.assignment_lv1.dto.MsgAndHttpStatusDto;
import com.sparta.assignment_lv1.serivce.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{id}")
    public CommentResponseDto createComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, HttpServletRequest request) {
        return commentService.createComment(id, requestDto, request);
    }

    //해당 게시글의 모든 댓글 가져오기 오름차순
    @GetMapping("/note/{id}")
    public List<CommentResponseDto> getComments(@PathVariable Long id, HttpServletRequest request){
        return commentService.getComments(id,request);
    }


    @PutMapping("/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, HttpServletRequest request) {
        return commentService.updateComment(id, requestDto, request);
    }

    @DeleteMapping("{id}")
    public MsgAndHttpStatusDto deleteComment(@PathVariable Long id, HttpServletRequest request) {
        return commentService.deleteComment(id, request);
    }
}
