package com._8attery.seesaw.service.battery;

import com._8attery.seesaw.domain.battery.BatteryRepository;
import com._8attery.seesaw.dto.api.response.ProjectResponseDto;
import com._8attery.seesaw.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com._8attery.seesaw.exception.BaseResponseStatus.DATABASE_ERROR;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BatteryService {

    private final BatteryRepository batteryRepository;

    @Transactional
    public Integer setUserActivityGoal(Long userId, Integer req) throws BaseException {
        try {
            batteryRepository.addUserActivityGoal(userId, req);

            return batteryRepository.findUserActivityGoal(userId);

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public Integer setUserSleepGoal(Long userId, Integer req) throws BaseException {
        try {
            batteryRepository.addUserSleepGoal(userId, req);

            return batteryRepository.findUserSleepGoal(userId);

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
