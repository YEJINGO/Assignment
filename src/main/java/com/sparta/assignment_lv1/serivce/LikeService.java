package com.sparta.assignment_lv1.serivce;

import com.sparta.assignment_lv1.dto.MsgAndHttpStatusDto;
import com.sparta.assignment_lv1.entity.Comment;
import com.sparta.assignment_lv1.entity.Likes;
import com.sparta.assignment_lv1.entity.Note;
import com.sparta.assignment_lv1.enums.CustomException;
import com.sparta.assignment_lv1.enums.ErrorCode;
import com.sparta.assignment_lv1.repository.CommentRepository;
import com.sparta.assignment_lv1.repository.LikeRepository;
import com.sparta.assignment_lv1.repository.NoteRepository;
import com.sparta.assignment_lv1.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final NoteRepository noteRepository;
    private final LikeRepository likeRepository;

    private final CommentRepository commentRepository;

    // 1. 노트 좋아요 개수 추출
    public ResponseEntity<MsgAndHttpStatusDto> updateNoteLike(Long noteId, UserDetailsImpl userDetails) {

        if (likeRepository.findByNote_IdAndUser_Id(noteId, userDetails.getUser().getId()) == null) {

            // 1. 라이크 만들기
            Note note = noteRepository.findById(noteId).orElseThrow(
                    () -> new CustomException(ErrorCode.NOT_FOUND_NOTE));
            Likes like = new Likes(note, userDetails);
            // 2. 라이크 저장
            likeRepository.save(like);
        } else {

            Likes like = likeRepository.findByNote_IdAndUser_Id(noteId, userDetails.getUser().getId());
            likeRepository.delete(like);
        }
        return ResponseEntity.ok(new MsgAndHttpStatusDto("좋아요가 성공적으로 업데이트 되었습니다.", HttpStatus.OK.value()));
//        return ResponseEntity.ok(likeRepository.findAllByNote_Id(noteId).size());
    }

    // 2. 게시물 좋아요 개수 추출
    public ResponseEntity<MsgAndHttpStatusDto> updateCommentLike(Long commentId, UserDetailsImpl userDetails) {


        if (likeRepository.findByComment_IdAndUser_Id(commentId, userDetails.getUser().getId()) == null) {

            // 1. 라이크 만들기
            Comment comment = commentRepository.findById(commentId).orElseThrow(
                    () -> new CustomException(ErrorCode.NOT_FOUND_NOTE));
            Likes like = new Likes(comment, userDetails);
            // 2. 라이크 저장
            likeRepository.save(like);
        } else {
            Likes like = likeRepository.findByComment_IdAndUser_Id(commentId, userDetails.getUser().getId());
            likeRepository.delete(like);
        }
        return ResponseEntity.ok(new MsgAndHttpStatusDto("좋아요가 성공적으로 업데이트 되었습니다.", HttpStatus.OK.value()));
    }
}
