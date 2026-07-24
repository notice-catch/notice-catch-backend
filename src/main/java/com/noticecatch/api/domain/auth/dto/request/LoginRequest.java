package com.noticecatch.api.domain.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private String authorizationCode; // 구글/카카오에서 발급받은 인가 코드
    private String socialType;  // "KAKAO" 또는 "GOOGLE"
}
