package com._8attery.seesaw.controller.charge;

import com._8attery.seesaw.domain.user.account.UserAccount;
import com._8attery.seesaw.dto.api.request.BatteryRequestDto;
import com._8attery.seesaw.dto.api.request.ChargeRequestDto;
import com._8attery.seesaw.dto.api.response.ChargeResponseDto;
import com._8attery.seesaw.exception.BaseException;
import com._8attery.seesaw.exception.BaseResponse;
import com._8attery.seesaw.service.charge.ChargeService;
import com._8attery.seesaw.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
//@Api(tags = {"6. Charge"})
@RestController
@RequiredArgsConstructor
public class ChargeController {

    private final UserService userService;

    private final ChargeService chargeService;

    // 고속충전 사용
    @PostMapping("/api/battery/charge")
    public BaseResponse<ChargeResponseDto> setCharge(@RequestBody ChargeRequestDto req, @AuthenticationPrincipal UserAccount userAccount) throws BaseException {
        Long userId = userService.resolveUserById(userAccount.getUserId()).getId();

        try {
            ChargeResponseDto res = chargeService.setUserCharge(userId, req.getValueId(), req.getChargeName(), req.getCreatedAt());

            return new BaseResponse<>(res);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
