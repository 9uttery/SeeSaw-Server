package com._8attery.seesaw.service.battery;

import com._8attery.seesaw.domain.battery.Battery;
import com._8attery.seesaw.domain.battery.BatteryRepository;
import com._8attery.seesaw.domain.charge.Charge;
import com._8attery.seesaw.dto.api.response.*;
import com._8attery.seesaw.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

            Integer curBattery = batteryRepository.findUserCurActivity(userId);
            Integer varBattery = curBattery + variation;
            if (varBattery >= 100) {
                batteryRepository.updateCurBattery100(userId);
            } else {
                batteryRepository.updateCurBattery(userId, variation); // 배터리 갱신
            }
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
            LocalDateTime endDate = LocalDate.now().atStartOfDay();  // Current date and time
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

            LocalDateTime endDate = LocalDate.now().atStartOfDay();  // Current date and time
            LocalDateTime startDate = endDate.minusDays(30);  // 30 days ago

            List<BatteryDataVariationDto> variationData = batteryRepository.findUserBatteryVariation(batteryId, startDate, endDate);
            List<BatteryChargeVariationDto> chargeData = batteryRepository.findUserBatteryCharge(userId, startDate, endDate);

            List<BatteryVariationResponseDto> combinedData = new ArrayList<>();

            // Merge variation and charge data based on the date
            for (BatteryDataVariationDto variation : variationData) {
                BatteryVariationResponseDto combinedDto = new BatteryVariationResponseDto();

                combinedDto.setDate(variation.getDate());
                combinedDto.setSleep(new BatteryVariationResponseDto.Sleep( variation.getCurSleep(), variation.getGoalSleep(), variation.getSleepVariation() ));
                combinedDto.setActivity(new BatteryVariationResponseDto.Activity( variation.getCurActivity(), variation.getGoalActivity(), variation.getActivityVariation() ));


                // Find matching charge data for the date
                for (BatteryChargeVariationDto charge : chargeData) {
                    if (charge.getDate().isEqual(variation.getDate())) {

                        combinedDto.setCharge(new BatteryVariationResponseDto.Charge( charge.getChargeName(), charge.getValueName(), charge.getChargeVariation() ));
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

            // 아직 안 온 날은 color 4 설정
            LocalDate date = LocalDate.now();

            int todayYear = date.getYear();       // Extract the year
            int todayMonth = date.getMonthValue();  // Extract the month as an integer (1-12)
            int todayDay = date.getDayOfMonth();  // Extract the day of the month

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
                        if (todayYear == year && todayMonth == month && todayDay < activityRes.getDay()) {
                            activityRes.setActivity(0);
                            activityRes.setColor(4);
                        } else {
                            activityRes.setActivity(0);
                            activityRes.setColor(0);
                        }
                    }
                }
            }

            return res;

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 특정 기간 동안의 수면 내역 조회
    public List<SleepResponseDto> getUserSleep(Long userId, Integer year, Integer month) throws BaseException {
        try {
            Long batteryId = batteryRepository.findUserBatteryId(userId);
            Integer sleepGoal = batteryRepository.findUserSleepGoal(userId);

            List<SleepDto> resultList = batteryRepository.findUserSleep(batteryId, year, month);
            List<SleepResponseDto> res = new ArrayList<>();

            // 1,3,5,7,8,10,12 -> 31일
            // 2,4,6,9,11 -> 30일 (로 치자 일단)
            if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month ==12) {
                for (int i = 1; i <= 31; i++) {
                    SleepResponseDto sleepDto = new SleepResponseDto();
                    sleepDto.setDay(i);
                    res.add(sleepDto);
                }
            } else if (month == 2 || month == 4 || month == 6 || month == 9 || month == 11) {
                for (int i = 1; i <= 30; i++) {
                    SleepResponseDto sleepDto = new SleepResponseDto();
                    sleepDto.setDay(i);
                    res.add(sleepDto);
                }
            }

            // 아직 안 온 날은 color 4 설정
            LocalDate date = LocalDate.now();

            int todayYear = date.getYear();       // Extract the year
            int todayMonth = date.getMonthValue();  // Extract the month as an integer (1-12)
            int todayDay = date.getDayOfMonth();  // Extract the day of the month

            for (SleepResponseDto sleepRes : res) {

                for (SleepDto result : resultList) {
                    if (result.getDay() == sleepRes.getDay()) {
                        sleepRes.setSleep(result.getSleep());

                        if (result.getSleep() < sleepGoal*0.5) {
                            sleepRes.setColor(3);
                        } else if (result.getSleep() < sleepGoal && result.getSleep() >= sleepGoal*0.5) {
                            sleepRes.setColor(2);
                        } else if (result.getSleep() >= sleepGoal) {
                            sleepRes.setColor(1);
                        }
                        break;
                    } else {
                        if (todayYear == year && todayMonth == month && todayDay < sleepRes.getDay()) {
                            sleepRes.setSleep(0);
                            sleepRes.setColor(4);
                        } else {
                            sleepRes.setSleep(0);
                            sleepRes.setColor(0);
                        }
                    }
                }

                Integer curSleep = batteryRepository.findUserCurSleep(userId);
                if (todayDay == sleepRes.getDay() && curSleep != null) {
                    sleepRes.setSleep(curSleep);
                    // 오늘 수면량 입력했으면 따로 확인
                    if (curSleep < sleepGoal * 0.5) {
                        sleepRes.setColor(3);
                    } else if (curSleep < sleepGoal && curSleep >= sleepGoal * 0.5) {
                        sleepRes.setColor(2);
                    } else if (curSleep >= sleepGoal) {
                        sleepRes.setColor(1);
                    }
                }
            }

            return res;

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 현재 배터리 상태 조회 -> 현재 배터리 잔량, 고속충전/활동량/수면 현황
    public BatteryResponseDto getUserBattery(Long userId) throws BaseException {
        try {
            BatteryResponseDto res = new BatteryResponseDto();

            // 1. Battery 객체 얻어와서 curBattery, curActivity, activityGoal, curSleep, sleepGoal 설정 & isCharged 정보
            Battery battery = batteryRepository.findUserBattery(userId);
            res.setCurBattery(battery.getCurBattery());
            res.setCurActivity(battery.getCurActivity());
            res.setActivityGoal(battery.getActivityGoal());
            res.setCurSleep(battery.getCurSleep());
            res.setSleepGoal(battery.getSleepGoal());

            // 2. Charge 에서 오늘 고속충전 name, valueId 가져와서 chargeName 설정
            Optional<Charge> charge = batteryRepository.findUserCharge(userId);
            if (charge.isPresent()) {
                res.setChargeName(charge.get().getName());

                // 3. Value 객체 얻어와서 valueName 설정
                Long valueId = charge.get().getValue().getId();
                String valueName = batteryRepository.findUserValue(valueId);
                res.setValueName(valueName);
            } else {
                res.setChargeName(null);
                res.setValueName(null);
            }

            return res;

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
