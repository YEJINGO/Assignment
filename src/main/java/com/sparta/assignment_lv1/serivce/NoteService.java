package com.sparta.assignment_lv1.serivce;

import com.sparta.assignment_lv1.dto.MsgAndHttpStatusDto;
import com.sparta.assignment_lv1.dto.NoteRequestDto;
import com.sparta.assignment_lv1.dto.NoteResponseDto;
import com.sparta.assignment_lv1.entity.Note;
import com.sparta.assignment_lv1.entity.User;
import com.sparta.assignment_lv1.jwt.JwtUtil;
import com.sparta.assignment_lv1.repository.NoteRepository;
import com.sparta.assignment_lv1.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
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
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
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
                () -> new IllegalArgumentException("해당 게시물이 없습니다. id=" + id)
        );
        return new NoteResponseDto(entity);
    }


    @Transactional
    public NoteResponseDto updateNote(Long id, NoteRequestDto requestDto, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request); // 토큰 가져오기

        if (token != null) {
            Note note = validate(id, token);
            note.update(requestDto);
            return new NoteResponseDto(note);
        } else
            return null;
    }


//    @Transactional
//    public Note updateNote(Long id, NoteResponseDto responseDto) {
//        Note note = noteRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("아이디가 존재하지 않습니다."));
//
//        if (!note.getPassword().equals(requestDto.getPassword())) {
//            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
//        }
//
//        note.update(responseDto);
//        return note;
//    }


    @Transactional
    public MsgAndHttpStatusDto deleteNote(Long id, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request); // 토큰 가져오기

        if (token != null) {
            Note note = validate(id, token);
            noteRepository.delete(note);
            return new MsgAndHttpStatusDto("게시글 삭제 성공", HttpStatus.OK.value());
            //이렇게 new 연산자로 생성해주면, 위에서는 따로 DelResponseDto result = new DelResponseDto(); 이런거 안 만들어줘도 됨
            //return new BoardResponseDto("게시글 삭제 성공", HttpStatus.OK.value());

        } else {
            return new MsgAndHttpStatusDto("게시글 작성자만 삭제 가능", HttpStatus.OK.value());
            //return new BoardResponseDto("게시글 작성자만 삭제 가능", HttpStatus.OK.value());
        }
    }

    private Note validate(Long id, String token) {
        Claims claims;
        if (jwtUtil.validateToken(token)) {
            claims = jwtUtil.getUserInfoFromToken(token);
        } else {
            throw new IllegalArgumentException("Token Error");
        }
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                //매개변수가 의도치 않는 상황 유발시
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        Note note = noteRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        return note;
    }
}