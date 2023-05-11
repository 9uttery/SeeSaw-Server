package com._8attery.seesaw.dto.auth.response;

import lombok.*;

@Getter
@Setter
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupResponseDto {

    // 회원가입 성공 시 agreeMarketing, nickName 응답
    private Boolean agreeMarketing;

    private String nickName;
}
