package com._8attery.seesaw.service.user;

import com._8attery.seesaw.domain.user.User;

public interface UserService {
    // 객체가 있는지 조회한 뒤 가져오는 메소드
    User resolveUserById(Long userId);

    // 비즈니스 로직
}
