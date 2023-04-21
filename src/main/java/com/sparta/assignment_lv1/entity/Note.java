package com.sparta.assignment_lv1.entity;

import com.sparta.assignment_lv1.dto.NoteRequestDto;
import jakarta.persistence.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Note extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column
    private String username;
    @Column(nullable = false)
    private String contents;
    @Column
//    @JsonIgnore
    private String password;

    @Column
    private Long userId;

    public Note(NoteRequestDto requestDto, User user) {
        this.username = user.getUsername();
        this.title = requestDto.getTitle();
        this.password = user.getPassword();
        this.contents = requestDto.getContents();
        this.userId = user.getId();
    }

    public void update(NoteRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();

    }

}

