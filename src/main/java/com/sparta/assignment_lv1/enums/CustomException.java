package com.sparta.assignment_lv1.enums;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private ErrorCode errorCode;

    public CustomException( ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
