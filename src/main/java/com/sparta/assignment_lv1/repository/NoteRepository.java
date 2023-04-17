package com.sparta.assignment_lv1.repository;

import com.sparta.assignment_lv1.dto.NoteResponseDto;
import com.sparta.assignment_lv1.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllByOrderByModifiedAtDesc();
}
