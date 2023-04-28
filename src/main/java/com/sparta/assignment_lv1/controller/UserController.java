package com.sparta.assignment_lv1.controller;

import com.sparta.assignment_lv1.dto.LoginRequestDto;
import com.sparta.assignment_lv1.dto.MsgAndHttpStatusDto;
import com.sparta.assignment_lv1.dto.SignupRequestDto;
import com.sparta.assignment_lv1.repository.UserRepository;
import com.sparta.assignment_lv1.serivce.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor // 생성자 자동 주입
@RequestMapping("/api/user") // url 공통 부분
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    //ResponseEntity: 결과값, 상태코드, 헤더값을 모두 프론트에 넘겨줄 수 있고, 에러코드 또한 섬세하게 설정해서 보내줄 수 있음 --> 구글링 필요, MsgResponseDto 의 데이터를 반환할 것임, signup 메소드 명
    //@RequestBody: HTTP Method 안의 body 값을 Mapping(key:value 로 짝지어줌), SignupRequestDto: 넘어오는 데이터를 받아주는 객체
    //@Valid: Controller 에서 유효성 검사를 할 곳에 붙임
    public ResponseEntity<MsgAndHttpStatusDto> signup(@Valid  @RequestBody SignupRequestDto signupRequestDto) {
        //signupRequestDto 에 데이터를 담아서, userService 로 응답을 보냄
        userService.signup(signupRequestDto);
        //MsgResponseDto 에서 선언한 타입(여기서는 String message, int statusCode)으로 반환하는데,
        //ResponseEntity.ok(): 상태코드를 반환
        return ResponseEntity.ok(new MsgAndHttpStatusDto("회원 가입 완료", HttpStatus.OK.value()));
    }

    // 로그인
    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<MsgAndHttpStatusDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        userService.login(loginRequestDto, response);
        return ResponseEntity.ok(new MsgAndHttpStatusDto("로그인 성공", HttpStatus.OK.value()));
    }

}
