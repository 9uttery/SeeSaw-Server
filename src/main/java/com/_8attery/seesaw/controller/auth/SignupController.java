package com._8attery.seesaw.controller.auth;

import com._8attery.seesaw.domain.user.account.UserAccount;
import com._8attery.seesaw.dto.auth.request.SignupRequestDto;
import com._8attery.seesaw.dto.auth.response.SignupResponseDto;
import com._8attery.seesaw.exception.BaseException;
import com._8attery.seesaw.exception.BaseResponse;
import com._8attery.seesaw.service.auth.SignupService;
import com._8attery.seesaw.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
//@Api(tags = {"1. Auth"})
@RestController
@RequiredArgsConstructor
public class SignupController {

    private final UserService userService;

    private final SignupService signupService;

    @PostMapping("/auth/signup")
    public BaseResponse<SignupResponseDto> signup(@RequestBody SignupRequestDto req, @AuthenticationPrincipal UserAccount userAccount) throws BaseException {

        Long userId = userService.resolveUserById(userAccount.getUserId()).getId();

        try {
            SignupResponseDto res = signupService.userSignup(req.getAgreeMarketing(), req.getEmail(), req.getNickName(), userId);
            return new BaseResponse<>(res);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
