package com.sparta.assignment_lv1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.assignment_lv1.dto.NoteRequestDto;
import com.sparta.assignment_lv1.dto.NoteResponseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Note extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String contents;
    @Column(nullable = false)
//    @JsonIgnore
    private String password;


    public Note(NoteRequestDto requestDto) {
        this.userName = requestDto.getUserName();
        this.title = requestDto.getTitle();
        this.password = requestDto.getPassword();
        this.contents = requestDto.getContents();
    }

    public void update(NoteRequestDto requestDto) {
        this.userName = requestDto.getUserName();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.password = requestDto.getPassword();

    }

}

