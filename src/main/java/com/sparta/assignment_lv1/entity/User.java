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
    private String username;
    @Column(nullable = false)
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
