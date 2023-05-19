package com._8attery.seesaw.controller.value;

import com._8attery.seesaw.domain.user.account.UserAccount;
import com._8attery.seesaw.dto.api.request.ProjectRequestDto;
import com._8attery.seesaw.dto.api.request.ValueRequestDto;
import com._8attery.seesaw.dto.api.response.ProjectResponseDto;
import com._8attery.seesaw.dto.api.response.ValueInfoResponseDto;
import com._8attery.seesaw.dto.api.response.ValueResponseDto;
import com._8attery.seesaw.exception.BaseException;
import com._8attery.seesaw.exception.BaseResponse;
import com._8attery.seesaw.service.user.UserService;
import com._8attery.seesaw.service.value.ValueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com._8attery.seesaw.exception.BaseResponseStatus.DATABASE_ERROR;
import static com._8attery.seesaw.exception.BaseResponseStatus.USERS_FAILED_POST_ID;

@Slf4j
//@Api(tags = {"3. Value"})
@RestController
@RequiredArgsConstructor
public class ValueController {

    private final UserService userService;

    private final ValueService valueService;

    // 사용자 가치 선택
    @PostMapping("/api/value")
    public BaseResponse<Integer> setValues(@RequestBody ValueRequestDto req, @AuthenticationPrincipal UserAccount userAccount) throws BaseException {
        Long userId = userService.resolveUserById(userAccount.getUserId()).getId();

        try {
            int result = valueService.set3Values(req.getValues(), userId);
            return new BaseResponse<>(result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 사용자 가치 조회
    @GetMapping("/api/value")
    public BaseResponse<List<ValueResponseDto>> getValues(@AuthenticationPrincipal UserAccount userAccount) throws BaseException {
        Long userId = userService.resolveUserById(userAccount.getUserId()).getId();

        List<ValueResponseDto> res = valueService.get3Values(userId);
        return new BaseResponse<>(res);
    }

    // 가치에 맞는 프로젝트, 고속 충전 조회
    @GetMapping("/api/value/{valueId}")
    public BaseResponse<ValueInfoResponseDto> getValueInfo(@PathVariable("valueId") Long valueId, @AuthenticationPrincipal UserAccount userAccount) throws BaseException {
        Long userId = userService.resolveUserById(userAccount.getUserId()).getId();

        try {
            ValueInfoResponseDto res = valueService.getUserValueInfo(userId, valueId);

            return new BaseResponse<>(res);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
