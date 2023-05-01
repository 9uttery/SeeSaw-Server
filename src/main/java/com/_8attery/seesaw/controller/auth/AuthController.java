package com._8attery.seesaw.controller.auth;

import com._8attery.seesaw.dto.auth.request.LoginRequestDto;
import com._8attery.seesaw.dto.auth.response.LoginResponseDto;
import com._8attery.seesaw.service.auth.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthServiceImpl authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        if (loginRequestDto.getProvider().equalsIgnoreCase("APPLE")) {
            return ResponseEntity.ok(authService.appleLogin(loginRequestDto));
        }
//        else if (loginRequestDto.getProvider().equalsIgnoreCase("KAKAO")) {
//            return ResponseEntity.ok(authService.kakaoLogin(loginRequestDto));
//        }
        else {
            throw new IllegalArgumentException("Provider값이 KAKAO 또는 APPLE이 아닙니다.");
        }
    }
}
