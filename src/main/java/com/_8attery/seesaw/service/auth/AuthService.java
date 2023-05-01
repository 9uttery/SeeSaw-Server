package com._8attery.seesaw.service.auth;

import com._8attery.seesaw.dto.auth.request.LoginRequestDto;
import com._8attery.seesaw.dto.auth.response.LoginResponseDto;

public interface AuthService {

    // 객체가 이미 존재하는지 판단하는 메소드
    boolean userExistsByEmail(String email);

    // 비즈니스 로직
    LoginResponseDto appleLogin(LoginRequestDto loginRequestDto);

//    LoginResponseDto kakaoLogin(LoginRequestDto loginRequestDto);
}
