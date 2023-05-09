package com._8attery.seesaw.controller.user;

import com._8attery.seesaw.domain.user.User;
import com._8attery.seesaw.domain.user.account.UserAccount;
import com._8attery.seesaw.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<User> getUserInfo(@AuthenticationPrincipal UserAccount userAccount) {
        return ResponseEntity.ok(userService.resolveUserById(userAccount.getUserId()));
    }
}
