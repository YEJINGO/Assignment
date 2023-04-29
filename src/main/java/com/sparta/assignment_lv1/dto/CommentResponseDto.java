package com.sparta.assignment_lv1.dto;

import com.sparta.assignment_lv1.entity.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CommentResponseDto {


    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private int commentLikeCount;

    public CommentResponseDto(Comment comment) {
        this.contents = comment.getContents();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.commentLikeCount = comment.getLikes().size();
    }

}
