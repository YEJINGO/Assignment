package com.sparta.assignment_lv1.dto;
import com.sparta.assignment_lv1.entity.Note;
import lombok.Getter;

@Getter
public class NoteResponseDto {

    private String title;
    private String userName;
    private String contents;

    public NoteResponseDto(Note note) {
        this.title = note.getTitle();
        this.userName = note.getUserName();
        this.contents = note.getContents();
    }
}


