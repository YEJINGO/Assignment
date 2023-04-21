package com.sparta.assignment_lv1.dto;
import com.sparta.assignment_lv1.entity.Note;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NoteResponseDto {

    private String title;
    private String username;
    private String contents;

    private LocalDateTime createdAt;


    public NoteResponseDto(Note note) {
        this.title = note.getTitle();
        this.username = note.getUsername();
        this.contents = note.getContents();
        this.createdAt = note.getCreatedAt();
    }
}


