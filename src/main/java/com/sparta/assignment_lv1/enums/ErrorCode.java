package com.sparta.assignment_lv1.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {


    ONLY_WRITER(400, "OnlyWriter", "작성자만 삭제/수정할 수 있습니다.")
    , DUPLICATION(400, "Duplication", "중복된 username 입니다.")
    , NOT_FOUND_USER(400, "NotFound", "등록된 사용자가 아닙니다.")
    , WRONG_ADMIN_TOKEN(400, "WrongAdminToken", "관리자 암호가 틀려 등록이 불가능합니다.")
    , WRONG_PASSWORD(400, "WrongPassword", "비밀번호가 일치하지 않습니다.")
    , NOT_FOUND_NOTE(400, "NotFoundNote", "해당 게시물이 없습니다.")
    , NOT_FOUND_UPDATE_NOTE(400, "NotFoundUpdateNote", "수정하고자 하는 게시글이 없습니다.")
    , ONLY_CAN_UPDATE(400, "OnlyCanUpdate", "해당 사용자 혹은 관리자가 아니면 게시글을 수정할 수 없습니다.")
    , ONLY_CAN_DELETE(400, "OnlyCanDelete", "해당 사용자 혹은 관리자가 아니면 게시글을 삭제할 수 없습니다.")
    , INVALID_TOKEN(400, "InvalidToken", "토큰이 유효하지 않습니다.")
    , NOT_FOUND_UPDATE_COMMENT(400, "NotFoundUpdateComment", "수정하고자 하는 댓글이 없습니다.")
    , ONLY_CAN_UPDATE_COMMENT(400, "OnlyCanUpdateComment", "해당 사용자가 작성인 혹은 관리자가 아니라면, 댓글을 수정할 수 없습니다.")
    , NOT_FOUND_Delete_COMMENT(400, "NotFoundDeleteComment", "삭제하고자 하는 댓글이 없습니다.")
    , ONLY_CAN_DELETE_COMMENT(400, "OnlyCanUpdateComment", "해당 사용자가 작성인 혹은 관리자가 아니라면, 댓글을 삭제할 수 없습니다.")
    , NOT_VALIDATE_SIGNUP_PASSWORD(400, "NotValidateSignupPassword", "비밀번호는 최소 8자 이상, 15자 이하이며 알파벳 대소문자와 숫자으로 구성되어야 합니다.")
    , NOT_VALIDATE_SIGNUP_USERNAME(400, "NotValidateSignupUsername", "username은 최소 4자 이상, 10자 이하이며 알파벳 소문자, 숫자로 구성되어야 합니다.")
    ;

    private int statusCode;
    private String code;
    private String message;

}
