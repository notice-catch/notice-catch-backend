package com.noticecatch.api.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OAuthUserInfo {
    private String socialId;
    private String email;
    private String nickname;
    private String socialType;
}
