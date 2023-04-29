package com.sparta.assignment_lv1.repository;

import com.sparta.assignment_lv1.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByNote_IdOrderByModifiedAtDesc(Long id);

}
