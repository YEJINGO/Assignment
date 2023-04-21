package com.sparta.assignment_lv1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignupRequestDto {

    private String username;
    private String password;
    private boolean admin = false;
    private String adminToken = "";
}
