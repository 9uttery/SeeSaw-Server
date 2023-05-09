package com._8attery.seesaw.dto.auth.kakao;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class KakaoPublicKeys {

    private List<KakaoPublicKey> keys;

}
