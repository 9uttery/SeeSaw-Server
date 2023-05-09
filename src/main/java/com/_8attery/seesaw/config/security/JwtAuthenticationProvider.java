package com._8attery.seesaw.config.security;

import com._8attery.seesaw.config.util.JwtTokenUtil;
import com._8attery.seesaw.exception.custom.TokenExpiredException;
import com._8attery.seesaw.exception.custom.UserUnauthorizedException;
import com._8attery.seesaw.service.user.account.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserAccountService userAccountService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication.getPrincipal() == null) {
            throw new UserUnauthorizedException("인증되지 않은 사용자입니다.");
        }

        if (jwtTokenUtil.isExpiredToken(authentication.getPrincipal().toString())) {
            throw new TokenExpiredException("만료된 토큰입니다.");
        }


        UserDetails userDetails = userAccountService.loadUserByUsername((String) authentication.getPrincipal());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

