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
import com.sparta.assignment_lv1.repository.LikeRepository;
import com.sparta.assignment_lv1.repository.NoteRepository;
import com.sparta.assignment_lv1.repository.UserRepository;
import com.sparta.assignment_lv1.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final NoteRepository noteRepository;


    @Transactional
    public ResponseEntity<CommentResponseDto> createComment(Long commentId, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {

        Note note = noteRepository.findById(commentId).orElseThrow(()
                -> new CustomException(ErrorCode.NOT_FOUND_NOTE));

        Comment comment = new Comment(commentRequestDto, note, userDetails.getUser());
        commentRepository.save(comment);

        return ResponseEntity.ok(new CommentResponseDto(comment));
    }


    public ResponseEntity<List<CommentResponseDto>> getComments(Long commentId) {

        List<CommentResponseDto> comments = commentRepository.findByNote_IdOrderByModifiedAtDesc(commentId)
                .stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(comments);
    }

    public ResponseEntity<CommentResponseDto> updateComment(Long commentId, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {

        User user = userDetails.getUser();
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_FOUND_UPDATE_COMMENT));

        if (comment.getUser().getId() == user.getId() || user.getRole().equals(UserRoleEnum.ADMIN)) {
            comment.updateComment(commentRequestDto);
            commentRepository.save(comment);
            return ResponseEntity.ok(new CommentResponseDto(comment));
        }
        return null;
    }

    public ResponseEntity<MsgAndHttpStatusDto> deleteComment(Long commentId, UserDetailsImpl userDetails) {

        User user = userDetails.getUser();
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_FOUND_Delete_COMMENT));

        if (Objects.equals(comment.getUser().getId(), user.getId()) || user.getRole().equals(UserRoleEnum.ADMIN)) {
            commentRepository.deleteById(commentId);
            return ResponseEntity.ok(new MsgAndHttpStatusDto("댓글이 삭제되었습니다.", HttpStatus.OK.value()));
        } else {
            throw new CustomException(ErrorCode.ONLY_CAN_DELETE_COMMENT);
        }
    }
}

