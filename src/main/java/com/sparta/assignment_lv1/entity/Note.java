package com.sparta.assignment_lv1.entity;

import com.sparta.assignment_lv1.dto.NoteRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
//    @Column
//    @JsonIgnore
//    private String password;
//    @Column
//    private Long userId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL) // mappedBy의 속성값은 외래키의 주인인 상대 Entity 의 필드명을 의미한다. note 는 연관관계 주인이 가지고 있는 필드명이다. -> comment의 user를 의미
    private List<Comment> comments = new ArrayList<>();

    public void addUser(User user) {
        this.user = user;
    }

    public void addComment(List<Comment> comments){
        this.comments = comments; }

    public Note(NoteRequestDto requestDto, User user) {
        this.username = user.getUsername();
        this.title = requestDto.getTitle();
//        this.password = user.getPassword();
        this.contents = requestDto.getContents();
        this.user = user;
    }

    public void update(NoteRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();

    }

}

