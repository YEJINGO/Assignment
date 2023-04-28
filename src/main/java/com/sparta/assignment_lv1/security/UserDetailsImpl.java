package com.sparta.assignment_lv1.security;

import com.sparta.assignment_lv1.entity.User;
import com.sparta.assignment_lv1.entity.UserRoleEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    private final User user; // 인증 완료된 User 객체
    private final String username; // 인증 완료된 User 의 ID
    private final String password; // 인증 완료된 User 의 pw

    public UserDetailsImpl(User user, String username, String password) {
        this.user = user;
        this.username = username;
        this.password = password;
    }

    //  인증완료된 User 를 가져오는 Getter
    public User user() {
        return user;
    }

    public User getUser() {
        return user;
    }

    @Override // 사용자의 권한 GrantedAuthority 로 추상화 및 반환
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRoleEnum role = user.getRole(); //1. 유저의 권한을 가져와서(user.getRole())
        String authority = role.getAuthority();  //2. 그것(role)을 String 값으로 만들고

        //3. 추상화해서 사용
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}