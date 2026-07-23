package com.noticecatch.api.global.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

public class UserAuthentication extends UsernamePasswordAuthenticationToken {

    public UserAuthentication(Long userId) {
        super(userId, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
