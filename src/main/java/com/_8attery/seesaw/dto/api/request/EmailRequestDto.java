package com._8attery.seesaw.dto.api.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class EmailRequestDto {
    @NotNull
    @NotBlank
    private String email;
}
