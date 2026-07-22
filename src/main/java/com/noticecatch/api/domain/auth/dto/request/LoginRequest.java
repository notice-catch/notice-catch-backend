package com.noticecatch.api.domain.auth.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {
    private String socialToken; // 소셜 Access Token
    private String socialType;  // "KAKAO" 또는 "GOOGLE"
}
