package com.sparta.assignment_lv1.controller;

import com.sparta.assignment_lv1.security.UserDetailsImpl;
import com.sparta.assignment_lv1.serivce.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeController {

    private final LikeService likeService;

    @PutMapping("/like/note/{note_id}")
    public ResponseEntity updateNoteLike(@PathVariable Long note_id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.updateNoteLike(note_id, userDetails);
    }


    @PutMapping("/like/comment/{comment_id}")
    public ResponseEntity updateCommentLike(@PathVariable Long comment_id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.updateCommentLike(comment_id, userDetails);
    }

