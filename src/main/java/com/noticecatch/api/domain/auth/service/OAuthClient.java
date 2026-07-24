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
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthClient {

    protected final WebClient webClient;

    // Google 환경변수
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedirectUri;

    // Kakao 환경변수
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret:}")
    private String kakaoClientSecret;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri;

    // 소셜 Access Token 발급
    public String getAccessToken(String socialType, String authorizationCode) {
        if ("GOOGLE".equalsIgnoreCase(socialType)) {
            return getGoogleAccessToken(authorizationCode);
        } else if ("KAKAO".equalsIgnoreCase(socialType)) {
            return getKakaoAccessToken(authorizationCode);
        }
        throw new ProjectException(UserErrorCode.INVALID_OAUTH_PROVIDER);
    }

    private String getGoogleAccessToken(String authorizationCode) {
        String decodedCode = URLDecoder.decode(authorizationCode, StandardCharsets.UTF_8);

        Map<String, String> params = Map.of(
                "code", decodedCode,
                "client_id", googleClientId,
                "client_secret", googleClientSecret,
                "redirect_uri", googleRedirectUri,
                "grant_type", "authorization_code"
        );

        return fetchAccessToken("https://oauth2.googleapis.com/token", createFormData(params));
    }

    private String getKakaoAccessToken(String authorizationCode) {
        String decodedCode = URLDecoder.decode(authorizationCode, StandardCharsets.UTF_8);

        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "authorization_code");
        params.put("client_id", kakaoClientId);
        params.put("redirect_uri", kakaoRedirectUri);
        params.put("code", decodedCode);

        if (kakaoClientSecret != null && !kakaoClientSecret.isBlank()) {
            params.put("client_secret", kakaoClientSecret);
        }

        return fetchAccessToken("https://kauth.kakao.com/oauth/token", createFormData(params));
    }

    // 2. 소셜 유저 정보 조회
    public OAuthUserInfo getUserInfo(String socialType, String socialToken) {
        if ("KAKAO".equalsIgnoreCase(socialType)) {
            return getKakaoUserInfo(socialToken);
        } else if ("GOOGLE".equalsIgnoreCase(socialType)) {
            return getGoogleUserInfo(socialToken);
        }
        throw new ProjectException(UserErrorCode.INVALID_OAUTH_PROVIDER);
    }

    private OAuthUserInfo getKakaoUserInfo(String socialToken) {
        try {
            Map<String, Object> response = webClient.get()
                    .uri("https://kapi.kakao.com/v2/user/me")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + socialToken)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=utf-8")
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
            throw new ProjectException(UserErrorCode.INVALID_OAUTH_TOKEN);
        }
    }

    private OAuthUserInfo getGoogleUserInfo(String socialToken) {
        try {
            Map<String, Object> response = webClient.get()
                    .uri("https://www.googleapis.com/oauth2/v3/userinfo")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + socialToken)
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
            throw new ProjectException(UserErrorCode.INVALID_OAUTH_TOKEN);
        }
    }

    // Map을 MultiValueMap(Form Data)으로 변환
    private MultiValueMap<String, String> createFormData(Map<String, String> params) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        params.forEach(formData::add);
        return formData;
    }

    // OAuth Access Token 요청 WebClient 공통 실행
    private String fetchAccessToken(String uri, MultiValueMap<String, String> formData) {
        try {
            Map<String, Object> response = webClient.post()
                    .uri(uri)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=utf-8")
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
            throw new ProjectException(UserErrorCode.INVALID_OAUTH_TOKEN);
        }
    }
}