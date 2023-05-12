package com._8attery.seesaw.controller;

import com._8attery.seesaw.domain.user.account.UserAccount;
import com._8attery.seesaw.dto.auth.request.SignupRequestDto;
import com._8attery.seesaw.dto.auth.response.SignupResponseDto;
import com._8attery.seesaw.exception.BaseException;
import com._8attery.seesaw.exception.BaseResponse;
import com._8attery.seesaw.service.battery.BatteryService;
import com._8attery.seesaw.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
//@Api(tags = {"5. Battery"})
@RestController
@RequiredArgsConstructor
public class BatteryController {

    private final UserService userService;

    private final BatteryService batteryService;

    // 활동량 목표 설정
    @PostMapping("/api/battery/goal/activity")
    public BaseResponse<Integer> setActivityGoal(@RequestBody Integer req, @AuthenticationPrincipal UserAccount userAccount) throws BaseException {
        Long userId = userService.resolveUserById(userAccount.getUserId()).getId();

        try {
            Integer res = batteryService.setUserActivityGoal(userId, req);

            return new BaseResponse<>(res);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 수면량 목표 설정
    @PostMapping("/api/battery/goal/sleep")
    public BaseResponse<Integer> setSleepGoal(@RequestBody Integer req, @AuthenticationPrincipal UserAccount userAccount) throws BaseException {
        Long userId = userService.resolveUserById(userAccount.getUserId()).getId();

        try {
            Integer res = batteryService.setUserSleepGoal(userId, req);

            return new BaseResponse<>(res);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
