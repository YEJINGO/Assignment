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
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final NoteRepository noteRepository;


    @Transactional
    public CommentResponseDto createComment(Long id, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request); // 토큰 가져오기

        Note note = noteRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_NOTE)
        );

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
            Comment comment = new Comment(commentRequestDto, note, user);

            commentRepository.save(comment);
            CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
            return commentResponseDto;

        } else {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }

    public List<CommentResponseDto> getComments(Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request); // 토큰 가져오기
        Note note = noteRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_NOTE)
        );
        if (token != null) {
            Claims claims;
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    //매개변수가 의도치 않는 상황 유발시
                    () -> new  CustomException(ErrorCode.NOT_FOUND_USER)
            );
            List<CommentResponseDto> comments = commentRepository.findAllByOrderByModifiedAtDesc()
                    .stream()
                    .map(CommentResponseDto::new)
                    .collect(Collectors.toList());
            return comments;
        }
        return null;
    }


    public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto, HttpServletRequest request) {
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

                Comment comment = commentRepository.findById(id).orElseThrow(() ->
                        new CustomException(ErrorCode.NOT_FOUND_UPDATE_COMMENT));

                if (comment.getUser().getId() == user.getId() || user.getRole().equals(UserRoleEnum.ADMIN)) {
                    comment.updateComment(commentRequestDto);
                    commentRepository.save(comment);
                    return new CommentResponseDto(comment);
                } else {
                    throw new CustomException(ErrorCode.ONLY_CAN_UPDATE_COMMENT);
                }
            }
            return null;
        }


        public MsgAndHttpStatusDto deleteComment (Long id, HttpServletRequest request){
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

                Comment comment = commentRepository.findById(id).orElseThrow(() ->
                        new CustomException(ErrorCode.NOT_FOUND_Delete_COMMENT));

                if (comment.getUser().getId() == user.getId() || user.getRole().equals(UserRoleEnum.ADMIN)) {
                    commentRepository.deleteById(id);
                    return new MsgAndHttpStatusDto("댓글이 삭제되었습니다.", HttpStatus.OK.value());
                } else {
                    throw new CustomException(ErrorCode.ONLY_CAN_DELETE_COMMENT);
                }
            }
            return null;
        }
    }