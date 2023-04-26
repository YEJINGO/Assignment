package com.sparta.assignment_lv1.serivce;

import com.sparta.assignment_lv1.dto.NoteRequestDto;
import com.sparta.assignment_lv1.dto.NoteResponseDto;
import com.sparta.assignment_lv1.entity.Comment;
import com.sparta.assignment_lv1.entity.Note;
import com.sparta.assignment_lv1.entity.User;
import com.sparta.assignment_lv1.entity.UserRoleEnum;
import com.sparta.assignment_lv1.enums.CustomException;
import com.sparta.assignment_lv1.enums.ErrorCode;
import com.sparta.assignment_lv1.jwt.JwtUtil;
import com.sparta.assignment_lv1.repository.CommentRepository;
import com.sparta.assignment_lv1.repository.NoteRepository;
import com.sparta.assignment_lv1.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;


    @Transactional
    public Note createNote(NoteRequestDto requestDto, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request); // 토큰 가져오기

        if (token != null) {
            Claims claims;
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    //매개변수가 의도치 않는 상황 유발시
                    () -> new CustomException(ErrorCode.NOT_FOUND_USER)
            );
            Note note = new Note(requestDto, user);
            noteRepository.save(note);
            return note;
        } else
            return null;
    }


    public List<NoteResponseDto> getNotes() { //전체
//        return noteRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).stream().map(NoteResponseDto::new).collect(Collectors.toList());
        List<NoteResponseDto> notes = noteRepository.findAllByOrderByModifiedAtDesc()
                .stream()
                .map(NoteResponseDto::new)
                .collect(Collectors.toList());
        return notes;
    }


    public NoteResponseDto getNote(Long id) { // 선택조회
        Note entity = noteRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_NOTE)
        );
        List<Comment> comments = commentRepository.findByNote_IdOrderByModifiedAtDesc(entity.getId());
        entity.addComment(comments);
//        List<CommentResponseDto> comments = commentRepository.findAllByOrderByModifiedAtDesc()
//                .stream()
//                .map(CommentResponseDto::new)
//                .collect(Collectors.toList());
//        return comments;
        return new NoteResponseDto(entity);
    }

    @Transactional
    public NoteResponseDto updateNote(Long id, NoteRequestDto requestDto, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request); // 토큰 가져오기
        Claims claims;

        Note note = noteRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_UPDATE_NOTE)
        );


        if (token != null) {

            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    //매개변수가 의도치 않는 상황 유발시
                    () -> new CustomException(ErrorCode.NOT_FOUND_USER)
            );

            if (note.getUser().getId() == user.getId() || user.getRole().equals(UserRoleEnum.ADMIN)) {
                note.update(requestDto);
                noteRepository.save(note);
                return new NoteResponseDto(note);
            } else {
                throw new CustomException(ErrorCode.ONLY_CAN_UPDATE);
            }
        }
        return null;
    }


    @Transactional
    public CustomException deleteNote(Long id, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request); // 토큰 가져오기
        Claims claims;

        Note note = noteRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_NOTE)
        );

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    //매개변수가 의도치 않는 상황 유발시
                    () -> new CustomException(ErrorCode.NOT_FOUND_USER)
            );

            if (note.getUser().getId() == user.getId() || user.getRole().equals(UserRoleEnum.ADMIN)) {
                noteRepository.delete(note);
                return new CustomException(ErrorCode.SUCCESS_COMMENT_DELETE);
            } else
                return new CustomException(ErrorCode.ONLY_CAN_DELETE);
        }
        return null;
    }
}

