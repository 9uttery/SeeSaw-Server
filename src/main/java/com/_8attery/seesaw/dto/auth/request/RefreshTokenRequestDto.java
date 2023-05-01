package com._8attery.seesaw.dto.auth.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class RefreshTokenRequestDto {

    @NotNull
    @JsonProperty("refresh_token")
    private String refreshToken;
}
