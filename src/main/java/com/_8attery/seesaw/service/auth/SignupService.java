package com._8attery.seesaw.service.auth;

import com._8attery.seesaw.domain.auth.SignupRepository;
import com._8attery.seesaw.dto.auth.response.SignupResponseDto;
import com._8attery.seesaw.exception.BaseException;
import com._8attery.seesaw.exception.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SignupService {

    private final SignupRepository signupRepository;

    public int checkEmail(Long userId) {
        int check = signupRepository.checkEmailExist(userId);

        return check;
    }

    @Transactional
    public SignupResponseDto userSignup(Boolean agreeMarketing, String email, String nickName, Long userId) throws BaseException {

        try {
            LocalDateTime today = LocalDateTime.now();
            signupRepository.setAgreeMarketing(agreeMarketing, userId);
            signupRepository.setNickName(nickName, userId);
            signupRepository.setEmail(email, userId);
            signupRepository.setCreateAt(today, userId);


            List<Object[]> info = signupRepository.getInfo(userId);

            SignupResponseDto res = new SignupResponseDto();
            for (Object[] row : info) {
                Boolean agreeMarketingResult = (Boolean) row[0];
                String nickNameResult = (String) row[1];
                res = new SignupResponseDto(agreeMarketingResult.booleanValue(), nickNameResult);
            }

            // 이미 배터리 정보 있으면 배터리 새로 추가 안되게 처리
            int battery = signupRepository.getBattery(userId);

            if (battery == 0) {
                // 배터리 초기 값 80 설정
                signupRepository.setBattery(userId);
            }

            return res;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

}
