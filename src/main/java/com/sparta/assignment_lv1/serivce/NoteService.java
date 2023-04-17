package com.sparta.assignment_lv1.serivce;

import com.sparta.assignment_lv1.dto.NoteRequestDto;
import com.sparta.assignment_lv1.dto.NoteResponseDto;
import com.sparta.assignment_lv1.entity.Note;
import com.sparta.assignment_lv1.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    @Transactional
    public Note createNote(NoteRequestDto requestDto) {
        Note note = new Note(requestDto);
        noteRepository.save(note);
        return note;
    }

    @Transactional
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
    public NoteResponseDto updateNote(Long id, NoteRequestDto requestDto) {
        Note note = noteRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다."));

        if (!note.getPassword().equals(requestDto.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        note.update(requestDto);
        return new NoteResponseDto(note);
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
    public String deleteNote(Long id, String password ) {
        Note note = noteRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다."));

        if (!note.getPassword().equals(password)) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        noteRepository.deleteById(id);
        return "성공쓰";
    }
}

//    @Transactional
//    public List<Note> getIdNote(Long id) {
//        noteRepository.findById(id);
//        return noteRepository.findById();
//    }
//}
