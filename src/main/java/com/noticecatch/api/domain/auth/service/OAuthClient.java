package com.noticecatch.api.domain.auth.service;

import com.noticecatch.api.domain.auth.dto.response.OAuthUserInfo;
import com.noticecatch.api.domain.user.exception.UserErrorCode;
import com.noticecatch.api.global.apiPayload.exception.ProjectException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Component
/*@Profile("!local") //로컬이 아닐 때만 실제 api 실행*/
@RequiredArgsConstructor
public class OAuthClient {

    protected final WebClient webClient;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedirectUri;

    // 1. 인가 코드를 이용해 소셜 Access Token 발급
    public String getAccessToken(String socialType, String authorizationCode) {
        if ("GOOGLE".equalsIgnoreCase(socialType)) {
            return getGoogleAccessToken(authorizationCode);
        }
        throw new ProjectException(UserErrorCode.INVALID_OAUTH_PROVIDER);
    }

    private String getGoogleAccessToken(String authorizationCode) {
        String decodedCode = URLDecoder.decode(authorizationCode, StandardCharsets.UTF_8);

        log.info("=== Google OAuth 디버깅 ===");
        log.info("Raw Code: {}", authorizationCode);
        log.info("Decoded Code: {}", decodedCode);
        log.info("ClientId: {}", googleClientId);
        log.info("ClientSecret: {}", googleClientSecret);
        log.info("RedirectUri: {}", googleRedirectUri);
        log.info("===========================");

        try {
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("code", decodedCode);
            formData.add("client_id", googleClientId);
            formData.add("client_secret", googleClientSecret);
            formData.add("redirect_uri", googleRedirectUri);
            formData.add("grant_type", "authorization_code");

            Map<String, Object> response = webClient.post()
                    .uri("https://oauth2.googleapis.com/token")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                    .block();

            if (response == null || !response.containsKey("access_token")) {
                throw new ProjectException(UserErrorCode.INVALID_OAUTH_TOKEN);
            }

            return (String) response.get("access_token");
        } catch (ProjectException e) {
            throw e;
        } catch (Exception e) {
            // 구글 토큰 교환 실패
            throw new ProjectException(UserErrorCode.INVALID_OAUTH_TOKEN);
        }
    }

    // 2. Access Token을 이용해 소셜 유저 정보 조회
    public OAuthUserInfo getUserInfo(String socialType, String socialToken) {
        /*if ("KAKAO".equalsIgnoreCase(socialType)) {
            return getKakaoUserInfo(socialToken);
        } else*/
        if ("GOOGLE".equalsIgnoreCase(socialType)) {
            return getGoogleUserInfo(socialToken);
        }
        // 지원하지 않는 socialType 처리
        throw new ProjectException(UserErrorCode.INVALID_OAUTH_PROVIDER);
    }

    /*private OAuthUserInfo getKakaoUserInfo(String socialToken) {
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
    }*/

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