package com._8attery.seesaw.service.auth;

import com._8attery.seesaw.domain.user.User;
import com._8attery.seesaw.dto.auth.request.LoginRequestDto;
import com._8attery.seesaw.dto.auth.request.RefreshTokenRequestDto;
import com._8attery.seesaw.dto.auth.response.LoginResponseDto;

public interface AuthService {

    // 객체가 이미 존재하는지 판단하는 메소드
    boolean userExistsByEmail(String email);

    // 객체가 존재하는지 판단하고 존재하면 객체를 반환하는 메소드
    User findUserById(Long userId);

    // 비즈니스 로직
    LoginResponseDto appleLogin(LoginRequestDto loginRequestDto);

    LoginResponseDto regenerateAccessToken(RefreshTokenRequestDto refreshTokenRequestDto);

    LoginResponseDto kakaoLogin(LoginRequestDto loginRequestDto);

    LoginResponseDto getLoginResponseDto(User user);

    LoginResponseDto generateAccessTokenByUserId(Long userId);

    Boolean checkEmail(String email);
}
