package com._8attery.seesaw.auth.apple;


import com._8attery.seesaw.dto.auth.apple.ApplePublicKeys;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "apple-public-key-client", url = "https://appleid.apple.com/auth")
public interface AppleClient {

    @GetMapping("/keys")
    ApplePublicKeys getApplePublicKeys();
}
