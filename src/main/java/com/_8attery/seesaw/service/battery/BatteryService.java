package com._8attery.seesaw.service.battery;

import com._8attery.seesaw.domain.battery.BatteryRepository;
import com._8attery.seesaw.dto.api.response.*;
import com._8attery.seesaw.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

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
    public List<BatteryVariationResponseDto> getUserBatteryData(Long userId) throws BaseException {
        try {
            Long batteryId = batteryRepository.findUserBatteryId(userId);

            LocalDateTime endDate = LocalDateTime.now();  // Current date and time
            LocalDateTime startDate = endDate.minusDays(30);  // 30 days ago

            List<BatteryDataVariationDto> variationData = batteryRepository.findUserBatteryVariation(batteryId, startDate, endDate);
            List<BatteryChargeVariationDto> chargeData = batteryRepository.findUserBatteryCharge(userId, startDate, endDate);

            List<BatteryVariationResponseDto> combinedData = new ArrayList<>();

            // Merge variation and charge data based on the date
            for (BatteryDataVariationDto variation : variationData) {
                BatteryVariationResponseDto combinedDto = new BatteryVariationResponseDto();
                combinedDto.setDate(variation.getDate());
                combinedDto.setCurSleep(variation.getCurSleep());
                combinedDto.setGoalSleep(variation.getGoalSleep());
                combinedDto.setSleepVariation(variation.getSleepVariation());
                combinedDto.setCurActivity(variation.getCurActivity());
                combinedDto.setGoalActivity(variation.getGoalActivity());
                combinedDto.setActivityVariation(variation.getActivityVariation());

                // Find matching charge data for the date
                for (BatteryChargeVariationDto charge : chargeData) {
                    if (charge.getDate().isEqual(variation.getDate())) {
                        combinedDto.setChargeName(charge.getChargeName());
                        combinedDto.setValueName(charge.getValueName());
                        combinedDto.setChargeVariation(charge.getChargeVariation());
                        break;  // Found a matching charge, exit the loop
                    }
                }

                combinedData.add(combinedDto);
            }

            return combinedData;

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }


    // 특정 기간 동안의 활동 내역 조회
    public List<ActivityResponseDto> getUserActivity(Long userId, Integer year, Integer month) throws BaseException {
        try {
            Long batteryId = batteryRepository.findUserBatteryId(userId);
            Integer activityGoal = batteryRepository.findUserActivityGoal(userId);

            List<ActivityDto> resultList = batteryRepository.findUserActivity(batteryId, year, month);
            List<ActivityResponseDto> res = new ArrayList<>();

            // 1,3,5,7,8,10,12 -> 31일
            // 2,4,6,9,11 -> 30일 (로 치자 일단)
            if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month ==12) {
                for (int i = 1; i <= 31; i++) {
                    ActivityResponseDto activityDto = new ActivityResponseDto();
                    activityDto.setDay(i);
                    res.add(activityDto);
                }
            } else if (month == 2 || month == 4 || month == 6 || month == 9 || month == 11) {
                for (int i = 1; i <= 30; i++) {
                    ActivityResponseDto activityDto = new ActivityResponseDto();
                    activityDto.setDay(i);
                    res.add(activityDto);
                }
            }

            for (ActivityResponseDto activityRes : res) {
                for (ActivityDto result : resultList) {
                    if (result.getDay() == activityRes.getDay()) {
                        activityRes.setActivity(result.getActivity());

                        if (result.getActivity() >= activityGoal + 50) {
                            activityRes.setColor(1);
                        } else if (result.getActivity() < activityGoal + 50 && result.getActivity() >= activityGoal) {
                            activityRes.setColor(2);
                        } else if (result.getActivity() < activityGoal) {
                            activityRes.setColor(3);
                        }
                        break;
                    } else {
                        activityRes.setActivity(0);
                        activityRes.setColor(0);
                    }
                }
            }

            return res;

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
