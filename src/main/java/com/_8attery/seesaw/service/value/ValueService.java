package com._8attery.seesaw.service.value;

import com._8attery.seesaw.domain.value.ValueRepository;
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
public class ValueService {

    private final ValueRepository valueRepository;

    @Transactional
    public int set3Values(List<String> values, Long userId) throws BaseException {

        int count = checkValues(userId);
        if (count == 3) {
            // 가치 3개 이미 존재
            throw new BaseException(BaseResponseStatus.VALUES_EXIST);
        }

        int result = 1;
        LocalDateTime currentDateTime = LocalDateTime.now();

        for (String value : values) {
            result = valueRepository.setUser3Values(value, currentDateTime, userId);
            if (result == 0)
                break;
        }

        return result;
    }

    public int checkValues(Long userId) {
        int count = valueRepository.checkValuesExist(userId);

        return count;
    }
}
