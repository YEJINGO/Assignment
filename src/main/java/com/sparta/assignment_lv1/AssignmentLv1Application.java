package com.sparta.assignment_lv1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AssignmentLv1Application {

    public static void main(String[] args) {
        SpringApplication.run(AssignmentLv1Application.class, args);
    }

}
