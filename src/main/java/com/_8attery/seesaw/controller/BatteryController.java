package com._8attery.seesaw.controller;

import com._8attery.seesaw.domain.user.account.UserAccount;
import com._8attery.seesaw.dto.api.request.BatteryRequestDto;
import com._8attery.seesaw.dto.api.response.BatteryPercentResponseDto;
import com._8attery.seesaw.exception.BaseException;
import com._8attery.seesaw.exception.BaseResponse;
import com._8attery.seesaw.service.battery.BatteryService;
import com._8attery.seesaw.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
//@Api(tags = {"5. Battery"})
@RestController
@RequiredArgsConstructor
public class BatteryController {

    private final UserService userService;

    private final BatteryService batteryService;

    // 활동량 목표 설정
    @PostMapping("/api/battery/goal/activity")
    public BaseResponse<Integer> setActivityGoal(@RequestBody BatteryRequestDto req, @AuthenticationPrincipal UserAccount userAccount) throws BaseException {
        Long userId = userService.resolveUserById(userAccount.getUserId()).getId();

        try {
            Integer res = batteryService.setUserActivityGoal(userId, req.getValue());

            return new BaseResponse<>(res);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 수면량 목표 설정
    @PostMapping("/api/battery/goal/sleep")
    public BaseResponse<Integer> setSleepGoal(@RequestBody BatteryRequestDto req, @AuthenticationPrincipal UserAccount userAccount) throws BaseException {
        Long userId = userService.resolveUserById(userAccount.getUserId()).getId();

        try {
            Integer res = batteryService.setUserSleepGoal(userId, req.getValue());

            return new BaseResponse<>(res);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 현재 활동량 설정
    @PostMapping("/api/battery/activity")
    public BaseResponse<Integer> setCurActivity(@RequestBody BatteryRequestDto req, @AuthenticationPrincipal UserAccount userAccount) throws BaseException {
        Long userId = userService.resolveUserById(userAccount.getUserId()).getId();

        try {
            Integer res = batteryService.setUserCurActivity(userId, req.getValue());

            return new BaseResponse<>(res);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 오늘 수면량 설정
    @PostMapping("/api/battery/sleep")
    public BaseResponse<Integer> setCurSleep(@RequestBody BatteryRequestDto req, @AuthenticationPrincipal UserAccount userAccount) throws BaseException {
        Long userId = userService.resolveUserById(userAccount.getUserId()).getId();

        try {
            Integer res = batteryService.setUserCurSleep(userId, req.getValue());

            return new BaseResponse<>(res);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 배터리 수준 조회 (7일 퍼센트)
    @GetMapping("/api/battery/history")
    public BaseResponse<List<BatteryPercentResponseDto>> getBatteryHistory(@AuthenticationPrincipal UserAccount userAccount) throws BaseException {
        Long userId = userService.resolveUserById(userAccount.getUserId()).getId();

        try {
            List<BatteryPercentResponseDto> res = batteryService.getUserBatteryHistory(userId);

            return new BaseResponse<>(res);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
