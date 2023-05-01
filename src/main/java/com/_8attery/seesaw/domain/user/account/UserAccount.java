package com._8attery.seesaw.domain.user.account;

import com._8attery.seesaw.domain.user.role.Role;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class UserAccount extends org.springframework.security.core.userdetails.User {
    private final Long userId;
    private final String email;
    private final Role role;

    public UserAccount(Long userId, String email, Role role) {
        super(email, "-", new ArrayList<Role>() { // 익명 클래스 정의
            {
                add(role);
            } //익명 클래스 내부 인스턴스 초기화 블록을 사용하여 ArrayList에 Role 객체를 추가
        });

        this.userId = userId;
        this.email = email;
        this.role = role;
    }
}
