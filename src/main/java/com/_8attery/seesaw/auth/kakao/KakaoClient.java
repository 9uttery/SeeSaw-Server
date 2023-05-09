package com._8attery.seesaw.auth.kakao;

import com._8attery.seesaw.dto.auth.kakao.KakaoPublicKeys;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "KakaoAuthClient",
        url = "https://kauth.kakao.com")
public interface KakaoClient {
    @Cacheable(cacheNames = "KakaoOICD", cacheManager = "kakaoOidcCacheManager")
    @GetMapping("/.well-known/jwks.json")
    KakaoPublicKeys getKakaoOIDCOpenKeys();
}
