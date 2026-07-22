package com.noticecatch.api.domain.auth.service;

import com.noticecatch.api.domain.auth.dto.OAuthUserInfo;
import com.noticecatch.api.global.apiPayload.code.GeneralErrorCode;
import com.noticecatch.api.global.apiPayload.exception.ProjectException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuthClient {

    private final WebClient webClient = WebClient.create();

    public OAuthUserInfo getUserInfo(String socialType, String socialToken) {
        if ("KAKAO".equalsIgnoreCase(socialType)) {
            return getKakaoUserInfo(socialToken);
        } else if ("GOOGLE".equalsIgnoreCase(socialType)) {
            return getGoogleUserInfo(socialToken);
        }
        throw new ProjectException(GeneralErrorCode.BAD_REQUEST);
    }

    private OAuthUserInfo getKakaoUserInfo(String socialToken) {
        try {
            Map<String, Object> response = webClient.get()
                    .uri("https://kapi.kakao.com/v2/user/me")
                    .header("Authorization", "Bearer " + socialToken)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            String socialId = String.valueOf(response.get("id"));
            Map<String, Object> kakaoAccount = (Map<String, Object>) response.get("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

            String email = (String) kakaoAccount.get("email");
            String nickname = (String) profile.get("nickname");

            return new OAuthUserInfo(socialId, email, nickname, "KAKAO");
        } catch (Exception e) {
            throw new ProjectException(GeneralErrorCode.UNAUTHORIZED);
        }
    }

    private OAuthUserInfo getGoogleUserInfo(String socialToken) {
        try {
            Map<String, Object> response = webClient.get()
                    .uri("https://www.googleapis.com/oauth2/v3/userinfo")
                    .header("Authorization", "Bearer " + socialToken)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            String socialId = (String) response.get("sub");
            String email = (String) response.get("email");
            String nickname = (String) response.get("name");

            return new OAuthUserInfo(socialId, email, nickname, "GOOGLE");
        } catch (Exception e) {
            throw new ProjectException(GeneralErrorCode.UNAUTHORIZED);
        }
    }
}
