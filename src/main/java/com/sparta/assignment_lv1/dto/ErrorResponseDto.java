package com.sparta.assignment_lv1.dto;

import com.sparta.assignment_lv1.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponseDto {

    private int statusCode;
//    private String code;
    private String message;

    public ErrorResponseDto(ErrorCode errorCode) {
        this.statusCode = errorCode.getStatusCode();
//        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();

    }
}
