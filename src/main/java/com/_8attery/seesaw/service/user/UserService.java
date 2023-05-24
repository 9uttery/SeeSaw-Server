package com._8attery.seesaw.service.user;

import com._8attery.seesaw.domain.user.User;
import com._8attery.seesaw.dto.api.request.NicknameRequestDto;
import com._8attery.seesaw.dto.api.response.UserHistoryResponseDto;
import com._8attery.seesaw.exception.BaseException;

public interface UserService {
    // 객체가 있는지 조회한 뒤 가져오는 메소드
    User resolveUserById(Long userId);

    void updateNickname(Long userId, NicknameRequestDto nicknameRequestDto) throws BaseException;

    String getUserNickname(Long userId) throws BaseException;

    UserHistoryResponseDto getUserHistory(Long userId) throws BaseException;

    void deleteUser(Long userId);
}
