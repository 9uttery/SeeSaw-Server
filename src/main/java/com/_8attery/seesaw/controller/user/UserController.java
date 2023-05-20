package com._8attery.seesaw.controller.user;

import com._8attery.seesaw.domain.user.account.UserAccount;
import com._8attery.seesaw.dto.api.request.NicknameRequestDto;
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

    // 닉네임 수정
    @PutMapping("api/user")
    public BaseResponse<String> getUser (@RequestBody NicknameRequestDto req, @AuthenticationPrincipal UserAccount userAccount) throws BaseException {
        Long userId = userService.resolveUserById(userAccount.getUserId()).getId();

        try {
            userService.updateNickname(userId, req.getNickName());

            String nickName = userService.getUserNickname(userId);

            return new BaseResponse<>(nickName);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
