package com._8attery.seesaw.domain.user.role;

public enum Role implements org.springframework.security.core.GrantedAuthority {

    ROLE_USER, ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
