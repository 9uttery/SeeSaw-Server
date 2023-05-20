package com._8attery.seesaw.service.user;

import com._8attery.seesaw.domain.user.User;
import com._8attery.seesaw.domain.user.UserRepository;
import com._8attery.seesaw.exception.BaseException;
import com._8attery.seesaw.exception.custom.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com._8attery.seesaw.exception.BaseResponseStatus.DATABASE_ERROR;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User resolveUserById(Long userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 id를 가진 사용자를 찾을 수 없습니다."));
    }

    @Override
    @Transactional
    public void updateNickname(Long userId, String nickName) throws BaseException {
        try {
            userRepository.updateUserNickname(userId, nickName);

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Override
    public String getUserNickname(Long userId) throws BaseException {
        try {

            return userRepository.findUserNickname(userId);

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
