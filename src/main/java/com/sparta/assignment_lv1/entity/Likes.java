package com.sparta.assignment_lv1.entity;

import com.sparta.assignment_lv1.security.UserDetailsImpl;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
@RequiredArgsConstructor
public class Likes {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    @ManyToOne
    @JoinColumn(name = "comment_id")
    Comment comment;
    @ManyToOne
    @JoinColumn(name = "note_id")
    Note note;


    public Likes(Note note, UserDetailsImpl userDetails) {
        this.note = note;
        this.user = userDetails.getUser();
    }

    public Likes(Comment comment, UserDetailsImpl userDetails) {
        this.comment = comment;
        this.user = userDetails.getUser();
    }


}


