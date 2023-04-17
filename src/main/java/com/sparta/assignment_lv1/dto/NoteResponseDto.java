package com.sparta.assignment_lv1.dto;
import lombok.Getter;

@Getter
public class NoteResponseDto {

    private String title;
    private String userName;
    private String contents;

    public NoteResponseDto(String title, String userName, String contents) {
        this.title = title;
        this.userName = userName;
        this.contents = contents;
    }
}


