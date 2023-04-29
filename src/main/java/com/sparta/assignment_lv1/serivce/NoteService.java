package com.sparta.assignment_lv1.serivce;

import com.sparta.assignment_lv1.dto.MsgAndHttpStatusDto;
import com.sparta.assignment_lv1.dto.NoteRequestDto;
import com.sparta.assignment_lv1.dto.NoteResponseDto;
import com.sparta.assignment_lv1.entity.Comment;
import com.sparta.assignment_lv1.entity.Note;
import com.sparta.assignment_lv1.entity.User;
import com.sparta.assignment_lv1.entity.UserRoleEnum;
import com.sparta.assignment_lv1.enums.CustomException;
import com.sparta.assignment_lv1.enums.ErrorCode;
import com.sparta.assignment_lv1.repository.CommentRepository;
import com.sparta.assignment_lv1.repository.NoteRepository;
import com.sparta.assignment_lv1.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final CommentRepository commentRepository;


    @Transactional
    public ResponseEntity<Note> createNote(NoteRequestDto requestDto, UserDetailsImpl userDetails) {

        Note note = new Note(requestDto, userDetails.user());
        noteRepository.save(note);
        return ResponseEntity.ok(note);
    }

    public ResponseEntity<List<NoteResponseDto>> getNotes() { //전체

        List<Note> notes = noteRepository.findAllByOrderByModifiedAtDesc();
        List<NoteResponseDto> NoteResponseDtoList = new ArrayList<>();


        for (Note note : notes) {
            List<Comment> comments = commentRepository.findByNote_IdOrderByModifiedAtDesc(note.getId());
            note.addComment(comments);
            NoteResponseDtoList.add(new NoteResponseDto(note));
        }
        return ResponseEntity.ok(NoteResponseDtoList);
//        return NoteResponseDtoList;
    }


    public ResponseEntity<NoteResponseDto> getNote(Long note_id) { // 선택조회

        Note entity = noteRepository.findById(note_id).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_NOTE));

        List<Comment> comments = commentRepository.findByNote_IdOrderByModifiedAtDesc(entity.getId());
        entity.addComment(comments);
        return ResponseEntity.ok(new NoteResponseDto(entity));
//        return new NoteResponseDto(entity);
    }

    @Transactional
    public ResponseEntity<NoteResponseDto> updateNote(Long note_id, NoteRequestDto requestDto, UserDetailsImpl userDetails) {
        Note note = noteRepository.findById(note_id).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_UPDATE_NOTE));

        User user = userDetails.getUser();

        if (note.getUser().getId() == user.getId() || user.getRole().equals(UserRoleEnum.ADMIN)) {
            note.update(requestDto);
            noteRepository.save(note);
            return ResponseEntity.ok(new NoteResponseDto(note));
        } else {
            throw new CustomException(ErrorCode.ONLY_CAN_UPDATE);
        }
    }

    @Transactional
    public ResponseEntity<MsgAndHttpStatusDto> deleteNote(Long note_id, UserDetailsImpl userDetails) {

        Note note = noteRepository.findById(note_id).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_NOTE)
        );

        User user = userDetails.getUser();

        if (Objects.equals(note.getUser().getId(), user.getId()) || user.getRole().equals(UserRoleEnum.ADMIN)) {
            noteRepository.delete(note);
            return ResponseEntity.ok(new MsgAndHttpStatusDto("게시물이 삭제되었습니다.", HttpStatus.OK.value()));
//            return new MsgAndHttpStatusDto("게시물이 삭제되었습니다.", HttpStatus.OK.value());
        } else {
            throw new CustomException(ErrorCode.ONLY_CAN_DELETE);
        }
    }
}

