package com.sparta.assignment_lv1.dto;

import lombok.Getter;

@Getter
public class NoteRequestDto {
    // 클라이언트에서 넘어오는 title, userNam, contents 를 객체를 통해서 받는다.

    private String title;
    private String userName;
    private String contents;
    private String password;

}
