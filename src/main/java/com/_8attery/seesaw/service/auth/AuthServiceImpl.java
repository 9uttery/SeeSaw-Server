package com._8attery.seesaw.service.auth;

import com._8attery.seesaw.auth.apple.AppleOAuthUserProvider;
import com._8attery.seesaw.auth.kakao.OAuthOidcHelper;
import com._8attery.seesaw.config.util.JwtTokenUtil;
import com._8attery.seesaw.domain.user.User;
import com._8attery.seesaw.domain.user.UserRepository;
import com._8attery.seesaw.domain.user.provider.Provider;
import com._8attery.seesaw.domain.user.role.Role;
import com._8attery.seesaw.dto.auth.request.LoginRequestDto;
import com._8attery.seesaw.dto.auth.request.RefreshTokenRequestDto;
import com._8attery.seesaw.dto.auth.response.LoginResponseDto;
import com._8attery.seesaw.exception.custom.ConflictRequestException;
import com._8attery.seesaw.exception.custom.InvalidTokenException;
import com._8attery.seesaw.exception.custom.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 60L * 60 * 24 * 30; // 30일

    private final AppleOAuthUserProvider appleOAuthUserProvider;
    private final OAuthOidcHelper oAuthOidcHelper;
    private final JwtTokenUtil jwtTokenUtil;

    private final UserRepository userRepository;
    private final RedisTemplate<String, String> redisTemplate;


    @Override
    public boolean userExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("사용자의 ID로 사용자를 찾을 수 없습니다.")
        );
    }

    @Override
    public LoginResponseDto appleLogin(LoginRequestDto loginRequestDto) {
        String email = appleOAuthUserProvider.getEmailFromToken(loginRequestDto.getIdToken());
        log.info("Apple Login Request Email : {}", email);
        Optional<User> existingUser = userRepository.findByEmail(email);
        User targetUser;

        if (existingUser.isPresent()) { // 이미 존재하는 회원일 경우
            targetUser = existingUser.get();
            if (targetUser.getProvider() != Provider.APPLE) { // 이미 존재하는 회원이지만 provider가 다른 경우
                throw new ConflictRequestException("이미 Apple이 아닌 다른 소셜 로그인으로 가입한 이메일입니다.");
            }
        } else { // 새로운 회원일 경우
            targetUser = userRepository.save(User.builder() // DB에 새로운 사용자 저장
                    .email(email)
                    .role(Role.ROLE_USER)
                    .provider(Provider.valueOf(loginRequestDto.getProvider().toUpperCase())) // 대문자로 변환
                    .build());
        }

        return getLoginResponseDto(targetUser);
    }

    @Override
    public LoginResponseDto regenerateAccessToken(RefreshTokenRequestDto refreshTokenRequestDto) {
        String refreshToken = refreshTokenRequestDto.getRefreshToken();
        Long userId = jwtTokenUtil.getUserIdFromRefreshToken(refreshToken);
        String existingRefreshToken = redisTemplate.opsForValue().get(userId.toString()); // Redis에 저장된 refreshToken인지 확인

        if (existingRefreshToken != null && !existingRefreshToken.equals(refreshToken)) { // refreshToken이 같은 지 검증
            throw new InvalidTokenException("유효하지 않은 Refresh Token입니다.");
        }

        User existingUser = findUserById(userId);
        String newAccessToken = jwtTokenUtil.generateAccessToken(existingUser.getId(), existingUser.getEmail(), existingUser.getRole());
        String newRefreshToken = jwtTokenUtil.generateRefreshToken(existingUser.getId(), existingUser.getEmail());

        redisTemplate.opsForValue().set(userId.toString(), newRefreshToken, REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.SECONDS); // Redis에 저장 (key: userId, value: refreshToken)

        return LoginResponseDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    @Override
    public LoginResponseDto kakaoLogin(LoginRequestDto loginRequestDto) {
        String email = oAuthOidcHelper.getPayloadFromIdToken(loginRequestDto.getIdToken()).getEmail();
        log.info("Kakao Login Request Email : {}", email);

        Optional<User> existingUser = userRepository.findByEmail(email);
        User targetUser;

        if (existingUser.isPresent()) { // 이미 존재하는 회원일 경우
            targetUser = existingUser.get();
            if (targetUser.getProvider() != Provider.KAKAO) { // 이미 존재하는 회원이지만 provider가 다른 경우
                throw new ConflictRequestException("이미 Kakao가 아닌 다른 소셜 로그인으로 가입한 이메일입니다.");
            }
        } else { // 새로운 회원일 경우
            targetUser = userRepository.save(User.builder() // DB에 새로운 사용자 저장
                    .email(email)
                    .role(Role.ROLE_USER)
                    .provider(Provider.valueOf(loginRequestDto.getProvider().toUpperCase())) // 대문자로 변환
                    .build());
        }

        return getLoginResponseDto(targetUser);
    }

    @Override
    public LoginResponseDto getLoginResponseDto(User targetUser) {
        String accessToken = jwtTokenUtil.generateAccessToken(targetUser.getId(), targetUser.getEmail(), targetUser.getRole());
        String refreshToken = jwtTokenUtil.generateRefreshToken(targetUser.getId(), targetUser.getEmail());
        redisTemplate.opsForValue().set(targetUser.getId().toString(), refreshToken, REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.SECONDS); // Redis에 저장 (key: userId, value: refreshToken)

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public LoginResponseDto generateAccessTokenByUserId(Long userId) {
        User existingUser = findUserById(userId);
        String accessToken = jwtTokenUtil.generateAccessToken(existingUser.getId(), existingUser.getEmail(), existingUser.getRole());

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .build();
    }

}
