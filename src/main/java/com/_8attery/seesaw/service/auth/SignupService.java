package com._8attery.seesaw.service.auth;

import com._8attery.seesaw.domain.auth.SignupRepository;
import com._8attery.seesaw.dto.auth.response.SignupResponseDto;
import com._8attery.seesaw.exception.BaseException;
import com._8attery.seesaw.exception.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            if (checkEmail(userId) == 1) { // 이메일 이미 있으면 빼고 추가
                signupRepository.setAgreeMarketing(agreeMarketing, userId);
                signupRepository.setNickName(nickName, userId);
            } else  { // 이메일 없으면 전부 추가
                signupRepository.setAgreeMarketing(agreeMarketing, userId);
                signupRepository.setNickName(nickName, userId);
                signupRepository.setEmail(email, userId);
            }

            List<Object[]> info = signupRepository.getInfo(userId);

            SignupResponseDto res = new SignupResponseDto();
            for (Object[] row : info) {
                Boolean agreeMarketingResult = (Boolean) row[0];
                String nickNameResult = (String) row[1];
                res = new SignupResponseDto(agreeMarketingResult.booleanValue(), nickNameResult);
            }

            return res;
        } catch (Exception exception) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

}
