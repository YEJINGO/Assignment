package com.sparta.assignment_lv1.security;

import com.sparta.assignment_lv1.entity.User;
import com.sparta.assignment_lv1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // 빈으로 사용
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    //UserDetailsServiceImpl 은 DB 에서 user 를 조회하고, 인증한 다음, UserDetails 를 반환하고, UserDetails 를 사용해서 인증 객체를 만든다
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("UserDetailsServiceImpl.loadUserByUsername : " + username);

        //user 를 DB 에서 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        //DB 에서 조회를 해온 user, username, password 를 User 객체에 담아주면서 UserDetailsImpl 를 반환
        return new UserDetailsImpl(user, user.getUsername(), user.getPassword());
    }

}