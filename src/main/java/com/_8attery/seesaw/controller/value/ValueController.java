package com._8attery.seesaw.controller.value;

import com._8attery.seesaw.domain.user.account.UserAccount;
import com._8attery.seesaw.dto.api.request.ValueReq;
import com._8attery.seesaw.dto.api.response.ValueRes;
import com._8attery.seesaw.exception.BaseException;
import com._8attery.seesaw.exception.BaseResponse;
import com._8attery.seesaw.service.user.UserService;
import com._8attery.seesaw.service.value.ValueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
//@Api(tags = {"2.Video"})
@RestController
@RequiredArgsConstructor
public class ValueController {

    private final UserService userService;

    private final ValueService valueService;

    @PostMapping("/api/value")
    public BaseResponse<Integer> setValues(@RequestBody ValueReq req, @AuthenticationPrincipal UserAccount userAccount) throws BaseException {

        Long userId = userService.resolveUserById(userAccount.getUserId()).getId();

        try {
            int result = valueService.set3Values(req.getValues(), userId);
            return new BaseResponse<>(result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @GetMapping("/api/value")
    public BaseResponse<ValueRes> getValues(@AuthenticationPrincipal UserAccount userAccount) throws BaseException {

        Long userId = userService.resolveUserById(userAccount.getUserId()).getId();

        ValueRes res = valueService.get3Values(userId);

        return new BaseResponse<>(res);
    }

}
