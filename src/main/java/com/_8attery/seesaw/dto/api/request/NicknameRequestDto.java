package com._8attery.seesaw.dto.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NicknameRequestDto {
    private String email;
    @NotNull
    private String nickName;
}
