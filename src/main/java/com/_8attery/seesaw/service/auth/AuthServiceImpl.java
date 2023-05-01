package com._8attery.seesaw.service.auth;

import com._8attery.seesaw.auth.apple.AppleOAuthUserProvider;
import com._8attery.seesaw.config.util.JwtTokenUtil;
import com._8attery.seesaw.domain.user.User;
import com._8attery.seesaw.domain.user.UserRepository;
import com._8attery.seesaw.domain.user.provider.Provider;
import com._8attery.seesaw.domain.user.role.Role;
import com._8attery.seesaw.dto.auth.request.LoginRequestDto;
import com._8attery.seesaw.dto.auth.response.LoginResponseDto;
import com._8attery.seesaw.exception.custom.InvalidRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final AppleOAuthUserProvider appleOAuthUserProvider;
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;


    @Override
    public boolean userExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public LoginResponseDto appleLogin(LoginRequestDto loginRequestDto) {
        String email = appleOAuthUserProvider.getEmailFromToken(loginRequestDto.getIdToken());
        log.info("Apple Login Request Email : {}", email);

        if (userExistsByEmail(email)) {
            throw new InvalidRequestException("이미 존재하는 이메일입니다.");
        }

        User newUser = userRepository.save(User.builder() // DB에 새로운 사용자 저장
                .email(email)
                .role(Role.ROLE_USER)
                .provider(Provider.valueOf(loginRequestDto.getProvider().toUpperCase())) // 대문자로 변환
                .build());

        String accessToken = jwtTokenUtil.generateAccessToken(newUser.getId(), newUser.getEmail(), newUser.getRole());
        String refreshToken = jwtTokenUtil.generateRefreshToken(newUser.getId(), newUser.getEmail());
        // refreshToken을 DB에 저장해줘야 함 -> Redis랑 연결 후에 해야 할 듯?

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
