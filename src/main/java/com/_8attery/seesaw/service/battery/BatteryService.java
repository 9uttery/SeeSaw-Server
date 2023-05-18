package com._8attery.seesaw.service.battery;

import com._8attery.seesaw.domain.battery.BatteryRepository;
import com._8attery.seesaw.dto.api.response.BatteryPercentResponseDto;
import com._8attery.seesaw.dto.api.response.BatteryVariationResponseDto;
import com._8attery.seesaw.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com._8attery.seesaw.exception.BaseResponseStatus.DATABASE_ERROR;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BatteryService {

    private final BatteryRepository batteryRepository;

    // 활동량 목표 설정
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

    // 수면량 목표 설정
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

    // 현재 활동량 설정
    @Transactional
    public Integer setUserCurActivity(Long userId, Integer req) throws BaseException {
        try {
            batteryRepository.addUserCurActivity(userId, req);

            return batteryRepository.findUserCurActivity(userId);

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 오늘 수면량 설정
    @Transactional
    public Integer setUserCurSleep(Long userId, Integer req) throws BaseException {
        try {
            batteryRepository.addUserCurSleep(userId, req);

            // 배터리 증감 내역 레코드 추가 & 배터리 갱신
            String type = "SLEEP";
            Long batteryId = batteryRepository.findUserBatteryId(userId);
            LocalDateTime createdAt = LocalDateTime.now();

            Integer variation = 0; // 수면에 따른 배터리 증감
            Integer sleepGoal = batteryRepository.findUserSleepGoal(userId);
            if (req < sleepGoal*0.5) {
                variation = -20;
            } else if (sleepGoal*0.5 <= req && req < sleepGoal) {
                variation = -10;
            } else if (req >= sleepGoal) {
                variation = 10;
            }
            batteryRepository.updateCurBattery(userId, variation); // 배터리 갱신
            batteryRepository.addUserSleepVariation(batteryId, createdAt, type, variation); // 배터리 증감 내역 레코드 추가

            return batteryRepository.findUserCurSleep(userId);

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 배터리 수준 조회 (7일 퍼센트)
    @Transactional
    public List<BatteryPercentResponseDto> getUserBatteryHistory(Long userId) throws BaseException {
        try {
            LocalDateTime endDate = LocalDateTime.now();  // Current date and time
            LocalDateTime startDate = endDate.minusDays(7);  // 7 days ago

            return batteryRepository.findUserBatteryHistory(userId, startDate, endDate);

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 배터리 증감 조회 (30일 증감 내역)
    @Transactional
    public List<BatteryVariationResponseDto> getUserBatteryVariation(Long userId) throws BaseException {
        try {
            LocalDateTime endDate = LocalDateTime.now();  // Current date and time
            LocalDateTime startDate = endDate.minusDays(30);  // 7 days ago

            return batteryRepository.findUserBatteryVariation(userId, startDate, endDate);

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
