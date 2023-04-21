package com.sparta.assignment_lv1.repository;

import com.sparta.assignment_lv1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //Optional: null 을 반환하면 오류가 발생할 가능성이 매우 높은 경우에 '결과 없음'을 명확하게 드러내기 위해
    Optional<User> findByUsername(String username);

}