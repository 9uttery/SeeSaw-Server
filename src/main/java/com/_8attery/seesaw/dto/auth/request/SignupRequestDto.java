package com._8attery.seesaw.dto.auth.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {

    private Boolean agreeMarketing; // 마케팅 약관 동의 여부

    private String email;

    private String nickName;
}
