package com.sparta.assignment_lv1.entity;

import com.sparta.assignment_lv1.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "comments")
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String contents;

    @ManyToOne(targetEntity = Note.class)
    @JoinColumn(name = "note_id", nullable = false) //comment DB에 들어가는 key 값
    private Note note;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public void setBoard(Note note) {
        this.note = note;
    }

    public Comment(CommentRequestDto commentRequestDto, Note note, User user) {
        this.note = note;
        this.user = user;
        this.contents = commentRequestDto.getContents();

    }

    public void updateComment(CommentRequestDto commentRequestDto) {
        this.contents = commentRequestDto.getContents();

    }

}
