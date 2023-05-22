package com._8attery.seesaw.controller.user;

import com._8attery.seesaw.domain.user.account.UserAccount;
import com._8attery.seesaw.dto.api.request.NicknameRequestDto;
import com._8attery.seesaw.dto.api.response.UserHistoryResponseDto;
import com._8attery.seesaw.exception.BaseException;
import com._8attery.seesaw.exception.BaseResponse;
import com._8attery.seesaw.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
//@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

//    @GetMapping
//    public ResponseEntity<User> getUserInfo(@AuthenticationPrincipal UserAccount userAccount) {
//        return ResponseEntity.ok(userService.resolveUserById(userAccount.getUserId()));
//    }

    // 닉네임 조회
    @GetMapping("/api/user")
    public BaseResponse<String> getUser (@AuthenticationPrincipal UserAccount userAccount) throws BaseException {
        Long userId = userService.resolveUserById(userAccount.getUserId()).getId();

        try {
            String nickName = userService.getUserNickname(userId);

            return new BaseResponse<>(nickName);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    // 닉네임 수정
    @PutMapping("/api/user")
    public BaseResponse<String> updateUser (@RequestBody NicknameRequestDto req, @AuthenticationPrincipal UserAccount userAccount) throws BaseException {
        Long userId = userService.resolveUserById(userAccount.getUserId()).getId();

        try {
            userService.updateNickname(userId, req.getNickName());

            String nickName = userService.getUserNickname(userId);

            return new BaseResponse<>(nickName);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 회원 탈퇴 화면 - 함께 한 일수, 가치 목록 조회 API
    @GetMapping("/api/user/history")
    public BaseResponse<UserHistoryResponseDto> getHistory (@AuthenticationPrincipal UserAccount userAccount) throws BaseException {
        Long userId = userService.resolveUserById(userAccount.getUserId()).getId();

        UserHistoryResponseDto res = userService.getUserHistory(userId);

        return new BaseResponse<>(res);
    }
}
