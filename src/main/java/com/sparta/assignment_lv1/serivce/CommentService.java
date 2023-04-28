package com.sparta.assignment_lv1.serivce;

import com.sparta.assignment_lv1.dto.CommentRequestDto;
import com.sparta.assignment_lv1.dto.CommentResponseDto;
import com.sparta.assignment_lv1.dto.MsgAndHttpStatusDto;
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
import com.sparta.assignment_lv1.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final NoteRepository noteRepository;


    @Transactional
    public CommentResponseDto createComment(Long id, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {

        Note note = noteRepository.findById(id).orElseThrow(()
                -> new CustomException(ErrorCode.NOT_FOUND_NOTE));

        Comment comment = new Comment(commentRequestDto, note, userDetails.getUser());
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }


    public List<CommentResponseDto> getComments(Long id) {

        List<CommentResponseDto> comments = commentRepository.findByNote_IdOrderByModifiedAtDesc(id)
                .stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
        return comments;
    }

    public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {

        User user = userDetails.getUser();
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_FOUND_UPDATE_COMMENT));

        if (comment.getUser().getId() == user.getId() ||user.getRole().equals(UserRoleEnum.ADMIN)) {
            comment.updateComment(commentRequestDto);
            commentRepository.save(comment);
            return new CommentResponseDto(comment);
        }
        return null;
    }


    public MsgAndHttpStatusDto deleteComment(Long id, UserDetailsImpl userDetails) {

        User user = userDetails.getUser();
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_FOUND_Delete_COMMENT));

        if (Objects.equals(comment.getUser().getId(), user.getId()) || user.getRole().equals(UserRoleEnum.ADMIN)) {
            commentRepository.deleteById(id);
            return new MsgAndHttpStatusDto("댓글이 삭제되었습니다.", HttpStatus.OK.value());
        } else {
            throw new CustomException(ErrorCode.ONLY_CAN_DELETE_COMMENT);
        }
    }
}

