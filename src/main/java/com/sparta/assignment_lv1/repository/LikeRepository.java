package com.sparta.assignment_lv1.repository;

import com.sparta.assignment_lv1.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Likes, Long> {
    List<Likes> findAllByNote_Id(Long id);

    List<Likes> findAllByComment_Id(Long id);

    Likes findByNote_IdAndUser_Id(Long noteId, Long userId);

    Likes findByComment_IdAndUser_Id(Long commentId, Long userId);

}
