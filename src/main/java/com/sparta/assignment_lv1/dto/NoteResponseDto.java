package com.sparta.assignment_lv1.dto;

import com.sparta.assignment_lv1.entity.Note;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class NoteResponseDto {


    private String title;
    private String username;
    private String contents;

    private LocalDateTime createdAt;
    private List<CommentResponseDto> commentList;
    private int noteLikeCount;

    public NoteResponseDto(Note note) {
        this.title = note.getTitle();
        this.username = note.getUsername();
        this.contents = note.getContents();
        this.createdAt = note.getCreatedAt();
        this.commentList = note.getComments().stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
        this.noteLikeCount = note.getLikes().size();
    }
}


