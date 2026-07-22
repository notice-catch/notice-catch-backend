package com.noticecatch.api.domain.auth.service;

import com.noticecatch.api.domain.auth.dto.response.OAuthUserInfo;
import com.noticecatch.api.domain.user.exception.UserErrorCode;
import com.noticecatch.api.global.apiPayload.exception.ProjectException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
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
        // 지원하지 않는 socialType 처리
        throw new ProjectException(UserErrorCode.INVALID_OAUTH_PROVIDER);
    }

    private OAuthUserInfo getKakaoUserInfo(String socialToken) {
        try {
            Map<String, Object> response = webClient.get()
                    .uri("https://kapi.kakao.com/v2/user/me")
                    .header("Authorization", "Bearer " + socialToken)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                    .block();

            if (response == null) {
                throw new ProjectException(UserErrorCode.INVALID_OAUTH_TOKEN);
            }

            String socialId = String.valueOf(response.get("id"));

            @SuppressWarnings("unchecked")
            Map<String, Object> kakaoAccount = (Map<String, Object>) response.get("kakao_account");

            String email = null;
            String nickname = null;

            if (kakaoAccount != null) {
                email = (String) kakaoAccount.get("email");

                @SuppressWarnings("unchecked")
                Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
                if (profile != null) {
                    nickname = (String) profile.get("nickname");
                }
            }

            return new OAuthUserInfo(socialId, email, nickname, "KAKAO");
        } catch (ProjectException e) {
            throw e;
        } catch (Exception e) {
            // 카카오 서버 연동 실패 or 유효하지 않은 토큰
            throw new ProjectException(UserErrorCode.INVALID_OAUTH_TOKEN);
        }
    }

    private OAuthUserInfo getGoogleUserInfo(String socialToken) {
        try {
            Map<String, Object> response = webClient.get()
                    .uri("https://www.googleapis.com/oauth2/v3/userinfo")
                    .header("Authorization", "Bearer " + socialToken)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                    .block();

            if (response == null) {
                throw new ProjectException(UserErrorCode.INVALID_OAUTH_TOKEN);
            }

            String socialId = (String) response.get("sub");
            String email = (String) response.get("email");
            String nickname = (String) response.get("name");

            return new OAuthUserInfo(socialId, email, nickname, "GOOGLE");
        } catch (ProjectException e) {
            throw e;
        } catch (Exception e) {
            // 구글 서버 연동 실패 or 유효하지 않은 토큰
            throw new ProjectException(UserErrorCode.INVALID_OAUTH_TOKEN);
        }
    }
}
