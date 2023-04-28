package com.sparta.assignment_lv1.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @validation 사용해서 아이디, 비번 조건 만들어보기
 * 정규식 특수문자 다시 확인해보기
 */
@Getter
@NoArgsConstructor
@Entity(name = "users")
@Embeddable
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) // null 허용X, 중복 허용 X
//    @Size(min=4, max=10, message = "아이디는 최소 4자 이상, 10자 이하로 만들어야 합니다.")
//    @Pattern(regexp = "^[a-z0-9]*$") //정규식에서 *$는 문자열의 끝부분에서 0번 이상의 모든 문자를 나타낸다.
    private String username;
    @Column(nullable = false)
//    @Size(min=8, max=15, message = "비밀번호는 최소 8자 이상, 15자 이하로 만들어야 합니다.")
//    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(String username, String password, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }


}
